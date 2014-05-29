/*
 * $Id$
 * 
 * Copyright (c) 2007-13 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.utbm.gi.vi51.g3.framework.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.vecmath.Vector2d;

import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.util.random.RandomNumber;

import fr.utbm.gi.vi51.g3.framework.time.SimulationTimeManager;


/**
 * Abstract implementation of a situated environment.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class AbstractEnvironment implements Environment {

	private final Map<AgentAddress,AgentBody> bodies = new TreeMap<AgentAddress,AgentBody>();
	private final Set<SituatedObject> objects = new HashSet<SituatedObject>();
	private final SimulationTimeManager timeManager;
	private final double width;
	private final double height;
	private final Collection<EnvironmentListener> listeners = new ArrayList<EnvironmentListener>();
	private final AtomicBoolean changed = new AtomicBoolean();
	private final AtomicBoolean init = new AtomicBoolean(true);

	/**
	 * @param width is the width of the environment.
	 * @param height is the height of the environment.
	 * @param timeManager is the time manager to use.
	 */
	public AbstractEnvironment(double width, double height, SimulationTimeManager timeManager) {
		this.width = width;
		this.height = height;
		this.timeManager = timeManager;
	}

	public void implantSituatedObject(SituatedObject object){
		if(object != null){
			this.objects.add(object);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void spawnAgentBody(Animat<?> animat) {
		if (animat!=null) {
			AgentBody body = animat.spawnBody(this);
			if (body!=null) {
				double size = body.getSize();
				body.setPosition(rnd(size, getWidth()), rnd(size, getHeight()));
				body.setAngle(RandomNumber.nextDouble() * 2.* Math.PI);
				this.bodies.put(animat.getAddress(), body);
			}
		}
	}

	private double rnd(double s, double w) {
		double r = w - 3. * s;
		r = RandomNumber.nextDouble() * r;
		return r + s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void killAgentBody(AgentAddress animat) {
		this.bodies.remove(animat);
	}

	/** {@inheritDoc}
	 */
	@Override
	public void addEnvironmentListener(EnvironmentListener listener) {
		synchronized(this.listeners) {
			this.listeners.add(listener);
		}
	}

	/** {@inheritDoc}
	 */
	@Override
	public void removeEnvironmentListener(EnvironmentListener listener) {
		synchronized(this.listeners) {
			this.listeners.remove(listener);
		}
	}

	/** Invoked to create an environment event.
	 * 
	 * @return an environment event.
	 */
	protected EnvironmentEvent createEnvironmentEvent() {
		return new EnvironmentEvent(this);
	}

	/** Notifies listeners about changes in environment.
	 */
	protected void fireEnvironmentChange() {
		EnvironmentListener[] list;
		synchronized(this.listeners) {
			list = new EnvironmentListener[this.listeners.size()];
			this.listeners.toArray(list);
		}
		EnvironmentEvent event = createEnvironmentEvent();
		for(EnvironmentListener listener : list) {
			listener.environmentChanged(event);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SimulationTimeManager getTimeManager() {
		return this.timeManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getWidth() {
		return this.width;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getHeight() {
		return this.height;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AgentBody> getAgentBodies() {
		return Collections.unmodifiableCollection(this.bodies.values());
	}

	/**
	 * Clone the list of agents in the environment.
	 *
	 * @return a clone.
	 */
	protected Collection<AgentBody> cloneAgentBodies() {
		Collection<AgentBody> clone = new ArrayList<AgentBody>(this.bodies.size());
		clone.addAll(this.bodies.values());
		return clone;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AgentBody getAgentBodyFor(AgentAddress agentId) {
		return this.bodies.get(agentId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SituatedObject> getOtherObjects() {
		return Collections.unmodifiableCollection(this.objects);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void runBehaviour() {
		if (this.init.getAndSet(false)) {
			fireEnvironmentChange();
		}
		Collection<MotionInfluence> influences = new ArrayList<MotionInfluence>();
		MotionInfluence influence;
		for(AgentBody body : this.bodies.values()) {
			influence = body.consumeInfluence();
			if (influence!=null) {
				influences.add(influence);
			}
		}
		if (!influences.isEmpty()) {
			this.changed.set(false);
			applyInfluences(influences, this.timeManager);
			if (this.changed.get()) {
				fireEnvironmentChange();
			}
		}
		List<Perception> list;
		for(AgentBody body : this.bodies.values()) {
			list = computePerceptionsFor(body);
			if (list==null) list = Collections.emptyList();
			body.setPerceptions(list);
		}
	}

	/** Compute the perceptions for an agent body.
	 * 
	 * @param agent is the body of the perceiver.
	 * @return the list of the perceived object, never <code>null</code>
	 */
	protected abstract List<Perception> computePerceptionsFor(AgentBody agent);

	/** Detects conflicts between influences and applied resulting actions.
	 * 
	 * @param influences are the influences to apply.
	 * @param timeManager is the time manager of the environment.
	 */
	protected abstract void applyInfluences(Collection<MotionInfluence> influences, SimulationTimeManager timeManager);

	/** Compute a steering move according to the linear move and to
	 * the internal attributes of this object.
	 * 
	 * @param obj is the object to move.
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the linear instant motion.
	 */
	protected final Vector2d computeSteeringTranslation(MobileObject obj, Vector2d move, SimulationTimeManager clock) {
		if (obj instanceof AbstractMobileObject) {
			AbstractMobileObject o = (AbstractMobileObject)obj;
			return o.computeSteeringTranslation(move, clock);
		}
		throw new IllegalArgumentException("obj"); //$NON-NLS-1$
	}

	/** Compute a kinematic move according to the linear move and to
	 * the internal attributes of this object.
	 * 
	 * @param obj is the object to move.
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the linear instant motion.
	 */
	protected final Vector2d computeKinematicTranslation(MobileObject obj, Vector2d move, SimulationTimeManager clock) {
		if (obj instanceof AbstractMobileObject) {
			AbstractMobileObject o = (AbstractMobileObject)obj;
			return o.computeKinematicTranslation(move, clock);
		}
		throw new IllegalArgumentException("obj"); //$NON-NLS-1$
	}

	/** Compute a kinematic move according to the angular move and to
	 * the internal attributes of this object.
	 * 
	 * @param obj is the object to move.
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the angular instant motion.
	 */
	protected final double computeKinematicRotation(MobileObject obj, double move, SimulationTimeManager clock) {
		if (obj instanceof AbstractMobileObject) {
			AbstractMobileObject o = (AbstractMobileObject)obj;
			return o.computeKinematicRotation(move, clock);
		}
		throw new IllegalArgumentException("obj"); //$NON-NLS-1$
	}

	/** Compute a steering move according to the angular move and to
	 * the internal attributes of this object.
	 * 
	 * @param obj is the object to move.
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the angular instant motion.
	 */
	protected final double computeSteeringRotation(MobileObject obj, double move, SimulationTimeManager clock) {
		if (obj instanceof AbstractMobileObject) {
			AbstractMobileObject o = (AbstractMobileObject)obj;
			return o.computeSteeringRotation(move, clock);
		}
		throw new IllegalArgumentException("obj"); //$NON-NLS-1$
	}

	/** Move the given object.
	 * 
	 * @param obj is the object to move.
	 * @param instantTranslation is the linear motion in m
	 * @param instantRotation is the angular motion in r
	 */
	protected final void move(MobileObject obj, Vector2d instantTranslation, double instantRotation) {
		if (obj instanceof AbstractMobileObject) {
			AbstractMobileObject o = (AbstractMobileObject)obj;
			double duration = this.timeManager.getTimeStepDuration()/1000.;
			o.move(instantTranslation.getX(), instantTranslation.getY(), duration, getWidth(), getHeight());
			o.rotate(instantRotation, duration);
			this.changed.set(true);
		}
		else {
			throw new IllegalArgumentException("obj"); //$NON-NLS-1$
		}
	}

}