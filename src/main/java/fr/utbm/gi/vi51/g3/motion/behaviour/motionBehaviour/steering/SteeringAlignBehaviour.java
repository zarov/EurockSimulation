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
package fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering;

import javax.vecmath.Vector2d;

import fr.utbm.gi.vi51.g3.framework.util.GeometryUtil;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.AlignBehaviour;

/**
 * Steering Align Behaviour.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class SteeringAlignBehaviour implements AlignBehaviour<SteeringBehaviourOutput> {

	private final double stopRadius;
	private final double slowRadius;

	/**
	 * @param stopRadius is the angle between the current direction and the target direction
	 * under which the rotation for alignment is ignored.
	 * @param slowRadius is the angle between the current direction and the target direction
	 * under which the rotation is going slower.
	 */
	public SteeringAlignBehaviour(double stopRadius, double slowRadius) {
		this.stopRadius = stopRadius;
		this.slowRadius = slowRadius;
	}

	private void stop(SteeringBehaviourOutput output, double angularSpeed, double maxAngularAcc) {
		double acc = -Math.min(Math.abs(angularSpeed), maxAngularAcc);
		output.setAngular(Math.signum(angularSpeed)*acc);
	}

	private void brake(SteeringBehaviourOutput output, double angularSpeed, double maxAngularAcc, double rotation) {
		double t = angularSpeed / rotation; //this.timeToTarget;
		output.setAngular(-angularSpeed * t);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SteeringBehaviourOutput runAlign(Vector2d orientation, double angularSpeed, double maxAngularAcc, Vector2d target) {
		SteeringBehaviourOutput output = new SteeringBehaviourOutput();

		// Get the naive direction to target, with an angle representation
		double rotation = GeometryUtil.signedAngle(orientation,target);

		if (rotation!=0 && angularSpeed==0) {
			output.setAngular(Math.signum(rotation) * Math.min(maxAngularAcc,rotation));
		}
		else {
			// the result is already mapped to (-pi,pi) interval with signedAngle()
			double rotationSize = Math.abs(rotation);

			// Check if we are there
			if (rotationSize<this.stopRadius) {
				// try to stop
				stop(output, angularSpeed, maxAngularAcc);
			}
			else if (rotationSize<this.slowRadius) {
				brake(output, angularSpeed, maxAngularAcc, rotationSize);
			}
			else {
				// Turn as fast as possible
				output.setAngular(Math.signum(rotation) * maxAngularAcc);
			}
		}

		return output;
	}

}