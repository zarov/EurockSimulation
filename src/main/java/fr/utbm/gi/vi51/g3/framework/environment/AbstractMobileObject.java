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

import javax.vecmath.Vector2d;

import fr.utbm.gi.vi51.g3.framework.time.SimulationTimeManager;
import fr.utbm.gi.vi51.g3.framework.util.GeometryUtil;

/**
 * Abstract implementation of an object on the environment.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class AbstractMobileObject extends AbstractSituatedObject implements MobileObject {

	private final double maxLinearSpeed;
	private final double maxLinearAcceleration;
	private final double maxAngularSpeed;
	private final double maxAngularAcceleration;

	private double currentAngularSpeed = 0;
	private final Vector2d linearMove = new Vector2d();

	/**
	 * @param size of the object.
	 * @param maxLinearSpeed is the maximal linear speed.
	 * @param maxLinearAcceleration is the maximal linear acceleration.
	 * @param maxAngularSpeed is the maximal angular speed.
	 * @param maxAngularAcceleration is the maximal angular acceleration.
	 */
	public AbstractMobileObject(double size, double maxLinearSpeed, double maxLinearAcceleration, double maxAngularSpeed, double maxAngularAcceleration) {
		super(size);
		this.maxLinearSpeed = Math.abs(maxLinearSpeed);
		this.maxLinearAcceleration = Math.abs(maxLinearAcceleration);
		this.maxAngularAcceleration = Math.abs(maxAngularAcceleration);
		this.maxAngularSpeed = Math.abs(maxAngularSpeed);
	}

	/** {@inheritDoc}
	 */
	@Override
	public double getMaxLinearSpeed() {
		return this.maxLinearSpeed;
	}

	/** {@inheritDoc}
	 */
	@Override
	public double getMaxAngularSpeed() {
		return this.maxAngularSpeed;
	}

	/** {@inheritDoc}
	 */
	@Override
	public double getMaxLinearAcceleration() {
		return this.maxLinearAcceleration;
	}

	/** {@inheritDoc}
	 */
	@Override
	public double getMaxAngularAcceleration() {
		return this.maxAngularAcceleration;
	}

	/** {@inheritDoc}
	 */
	@Override
	public double getCurrentAngularSpeed() {
		return this.currentAngularSpeed;
	}

	/** {@inheritDoc}
	 */
	@Override
	public double getCurrentLinearSpeed() {
		return this.linearMove.length();
	}

	/** {@inheritDoc}
	 */
	@Override
	public Vector2d getCurrentLinearMotion() {
		return new Vector2d(this.linearMove);
	}

	/** {@inheritDoc}
	 */
	@Override
	protected void setAngle(double angle) {
		super.setAngle(angle);
		this.currentAngularSpeed = 0;
	}

	/** {@inheritDoc}
	 */
	@Override
	protected void setDirection(double dx, double dy) {
		super.setDirection(dx, dy);
		this.currentAngularSpeed = 0;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setPosition(double x, double y) {
		super.setPosition(x, y);
		this.linearMove.set(0,0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	Vector2d move(double dx, double dy, double simulationDuration, double worldWidth, double worldHeight) {
		Vector2d r = super.move(dx, dy, simulationDuration, worldWidth, worldHeight);
		if (simulationDuration>0) {
			this.linearMove.set(r.x, r.y);
			double distance = this.linearMove.length();
			if (distance>0) {
				this.linearMove.normalize();
				this.linearMove.scale(distance/simulationDuration);
			}
		}
		else {
			this.linearMove.set(0,0);
		}
		return r;
	}

	/** {@inheritDoc}
	 */
	@Override
	void rotate(double rotation, double simulationDuration) {
		super.rotate(rotation, simulationDuration);
		this.currentAngularSpeed = rotation / simulationDuration;
	}

	private Vector2d scaleVector(Vector2d v, double length, SimulationTimeManager clock) {
		Vector2d v2 = new Vector2d(v);
		if (v2.length()>0) v2.normalize();
		v2.scale(clock.perSecond(length));
		return v2;
	}

	/** Compute a steering move according to the linear move and to
	 * the internal attributes of this object.
	 * 
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the linear instant motion.
	 */
	Vector2d computeSteeringTranslation(Vector2d move, SimulationTimeManager clock) {
		Vector2d m = new Vector2d();
		m.add(this.linearMove,move);
		double lSpeed = m.length();
		if (lSpeed<0) lSpeed = 0.;
		if (lSpeed>this.maxLinearSpeed) lSpeed = this.maxLinearSpeed;

		return scaleVector(m, lSpeed, clock);
	}

	/** Compute a kinematic move according to the linear move and to
	 * the internal attributes of this object.
	 * 
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the linear instant motion.
	 */
	Vector2d computeKinematicTranslation(Vector2d move, SimulationTimeManager clock) {
		double lSpeed = move.length();
		if (lSpeed<0) lSpeed = 0.;
		if (lSpeed>this.maxLinearSpeed) lSpeed = this.maxLinearSpeed;

		return scaleVector(move, lSpeed, clock);
	}

	/** Compute a kinematic move according to the angular move and to
	 * the internal attributes of this object.
	 * 
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the angular instant motion.
	 */
	double computeKinematicRotation(double move, SimulationTimeManager clock) {
		return clock.perSecond(GeometryUtil.clamp(move, -getMaxAngularSpeed(), getMaxAngularSpeed()));
	}

	/** Compute a steering move according to the angular move and to
	 * the internal attributes of this object.
	 * 
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the angular instant motion.
	 */
	double computeSteeringRotation(double move, SimulationTimeManager clock) {
		return clock.perSecond(GeometryUtil.clamp(move, -getMaxAngularAcceleration(), getMaxAngularAcceleration()));
	}

}