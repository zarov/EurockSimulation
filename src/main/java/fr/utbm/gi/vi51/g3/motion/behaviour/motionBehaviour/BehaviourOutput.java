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
package fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour;

import javax.vecmath.Vector2d;

import fr.utbm.gi.vi51.g3.framework.environment.DynamicType;

/**
 * Output of a behaviour.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class BehaviourOutput {

	private final DynamicType type;
	private final Vector2d linear = new Vector2d();
	private double angular = 0;

	/**
	 * @param type is the type of the output.
	 */
	public BehaviourOutput(DynamicType type) {
		this.type = type;
	}

	/**
	 * @param outputToCopy is the output tp copy.
	 */
	public BehaviourOutput(BehaviourOutput outputToCopy) {
		assert(outputToCopy!=null);
		this.type = outputToCopy.getType();
		this.linear.set(outputToCopy.getLinear());
		this.angular = outputToCopy.getAngular();
	}

	/** Replies the type of the output.
	 * 
	 * @return the type of the output.
	 */
	public DynamicType getType() {
		return this.type;
	}

	/** Replies the linear output.
	 * 
	 * @return the linear output.
	 */
	public Vector2d getLinear() {
		return this.linear;
	}

	/** Replies the angular output.
	 * 
	 * @return the angular output.
	 */
	public double getAngular() {
		return this.angular;
	}

	/** Set the linear output.
	 * 
	 * @param linear
	 */
	public void setLinear(Vector2d linear) {
		assert(linear!=null);
		this.linear.set(linear);
	}

	/** Set the linear output.
	 * 
	 * @param dx
	 * @param dy
	 */
	public void setLinear(double dx, double dy) {
		this.linear.set(dx, dy);
	}

	/** Set the angular output.
	 * 
	 * @param angular
	 */
	public void setAngular(double angular) {
		this.angular = angular;
	}

	/** Set the linear output.
	 * 
	 * @param outputToCopy
	 */
	public void setLinear(BehaviourOutput outputToCopy) {
		if (outputToCopy!=null) {
			if (outputToCopy.getType()!=getType()) {
				throw new IllegalArgumentException();
			}
			this.linear.set(outputToCopy.getLinear());
		}
	}

	/** Set the angular output.
	 * 
	 * @param outputToCopy
	 */
	public void setAngular(BehaviourOutput outputToCopy) {
		if (outputToCopy!=null) {
			if (outputToCopy.getType()!=getType()) {
				throw new IllegalArgumentException();
			}
			this.angular = outputToCopy.getAngular();
		}
	}

	/** Set the linear and angular output.
	 * 
	 * @param outputToCopy
	 */
	public void set(BehaviourOutput outputToCopy) {
		if (outputToCopy!=null) {
			if (outputToCopy.getType()!=getType()) {
				throw new IllegalArgumentException();
			}
			this.linear.set(outputToCopy.getLinear());
			this.angular = outputToCopy.getAngular();
		}
	}

}