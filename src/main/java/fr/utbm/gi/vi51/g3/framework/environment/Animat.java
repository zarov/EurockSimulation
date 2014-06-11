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

import java.util.List;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.agent.AgentActivationPrototype;
import org.janusproject.kernel.status.Status;

/**
 * Abstract implementation of an animat.
 *
 * @param <ABT>
 *            is the type of the body supported by this agent.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@AgentActivationPrototype
public abstract class Animat<ABT extends AgentBody> extends Agent {

	private static final long serialVersionUID = -3753143150725086116L;

	private ABT body = null;

	/**
	 */
	public Animat() {
		//
	}

	/**
	 * Invoked by the simulator to create the body for the agent.
	 *
	 * @param in
	 *            is the environment to create the body for.
	 * @return the agent body.
	 */
	protected abstract ABT createBody(Environment in);

	/**
	 * Set the body.
	 *
	 * @param in
	 *            is the environment to create the body for.
	 * @return the agent body.
	 */
	ABT spawnBody(Environment in) {
		if (this.body == null) {
			this.body = createBody(in);
		}
		return this.body;
	}

	/**
	 * Replies the body of this animat.
	 *
	 * @return the body of this animat.
	 */
	private ABT getBody() {
		if (this.body == null) {
			throw new IllegalStateException();
		}
		return this.body;
	}

	/**
	 * Replies the orientation of the animat.
	 *
	 * @return the angle of orientation from (1,0).
	 */
	protected final double getAngle() {
		return getBody().getAngle();
	}

	/**
	 * Replies the current angular speed of the animat.
	 *
	 * @return the current angular speed of the animat.
	 */
	protected final double getCurrentAngularSpeed() {
		return getBody().getCurrentAngularSpeed();
	}

	/**
	 * Replies the current linear speed of the animat.
	 *
	 * @return the current linear speed of the animat.
	 */
	protected final double getCurrentLinearSpeed() {
		return getBody().getCurrentLinearSpeed();
	}

	/**
	 * Replies the current linear motion of the animat.
	 *
	 * @return the current linear motion of the animat.
	 */
	protected final Vector2d getCurrentLinearMotion() {
		return getBody().getCurrentLinearMotion();
	}

	/**
	 * Replies the orientation of the animat.
	 *
	 * @return the orientation direction of the animat.
	 */
	protected final Vector2d getDirection() {
		return getBody().getDirection();
	}

	/**
	 * Replies the max angular acceleration of this animat.
	 *
	 * @return the max angular acceleration of this animat.
	 */
	protected final double getMaxAngularAcceleration() {
		return getBody().getMaxAngularAcceleration();
	}

	/**
	 * Replies the max angular speed of this animat.
	 *
	 * @return the max angular speed of this animat.
	 */
	protected final double getMaxAngularSpeed() {
		return getBody().getMaxAngularSpeed();
	}

	/**
	 * Replies the max linear acceleration of this animat.
	 *
	 * @return the max linear acceleration of this animat.
	 */
	protected final double getMaxLinearAcceleration() {
		return getBody().getMaxLinearAcceleration();
	}

	/**
	 * Replies the max linear speed if this animat.
	 *
	 * @return the max linear speed of this animat.
	 */
	protected final double getMaxLinearSpeed() {
		return getBody().getMaxLinearSpeed();
	}

	/**
	 * Replies the position of the animat.
	 *
	 * @return the position of the animat.
	 */
	protected final Point2d getPosition() {
		return getBody().getPosition();
	}

	/**
	 * Replies the size of the animat.
	 *
	 * @return the size of this animat.
	 */
	protected final double getSize() {
		return getBody().getSize();
	}

	/**
	 * Replies the position of the animat.
	 *
	 * @return the x-coordinate of the position of this animat.
	 */
	protected final double getX() {
		return getBody().getX();
	}

	/**
	 * Replies the position of the animat.
	 *
	 * @return the y-coordinate of the position of this animat.
	 */
	protected final double getY() {
		return getBody().getY();
	}

	/**
	 * Replies all the perceived objects.
	 *
	 * @return the perceived objects.
	 */
	protected final List<Perception> getPerceivedObjects() {
		return getBody().getPerceivedObjects();
	}

	/**
	 * Invoked to send the influence to the environment.
	 *
	 * @param linearInfluence
	 *            is the linear influence to apply on the object.
	 * @param angularInfluence
	 *            is the angular influence to apply on the object.
	 */
	protected final void influenceKinematic(Vector2d linearInfluence,
			double angularInfluence) {
		getBody().influenceKinematic(linearInfluence, angularInfluence);
	}

	/**
	 * Invoked to send the influence to the environment.
	 *
	 * @param linearInfluence
	 *            is the linear influence to apply on the object.
	 */
	protected final void influenceKinematic(Vector2d linearInfluence) {
		getBody().influenceKinematic(linearInfluence);
	}

	/**
	 * Invoked to send the influence to the environment.
	 *
	 * @param angularInfluence
	 *            is the angular influence to apply on the object.
	 */
	protected final void influenceKinematic(double angularInfluence) {
		getBody().influenceKinematic(angularInfluence);
	}

	/**
	 * Invoked to send the influence to the environment.
	 *
	 * @param linearInfluence
	 *            is the linear influence to apply on the object.
	 * @param angularInfluence
	 *            is the angular influence to apply on the object.
	 */
	protected final void influenceSteering(Vector2d linearInfluence,
			double angularInfluence) {
		getBody().influenceSteering(linearInfluence, angularInfluence);
	}

	/**
	 * Invoked to send the influence to the environment.
	 *
	 * @param linearInfluence
	 *            is the linear influence to apply on the object.
	 */
	protected final void influenceSteering(Vector2d linearInfluence) {
		getBody().influenceSteering(linearInfluence);
	}

	/**
	 * Invoked to send the influence to the environment.
	 *
	 * @param angularInfluence
	 *            is the angular influence to apply on the object.
	 */
	protected final void influenceSteering(double angularInfluence) {
		getBody().influenceSteering(angularInfluence);
	}

	/**
	 * Run the behaviour of the animat
	 *
	 * @return the status of the execution, usually: StatusFactory.ok(this).
	 */
	@Override
	public abstract Status live();

}