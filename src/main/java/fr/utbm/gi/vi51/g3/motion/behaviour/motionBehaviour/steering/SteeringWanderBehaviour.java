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

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import fr.utbm.gi.vi51.g3.framework.util.GeometryUtil;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.WanderBehaviour;

/**
 * Steering Wander Behaviour.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class SteeringWanderBehaviour implements WanderBehaviour<SteeringBehaviourOutput> {

	private final SteeringFaceBehaviour faceBehaviour;
	private final double circleDistance;
	private final double circleRadius;
	private final double maxRotation;
	private double rotation;

	/**
	 * @param circleDistance is the distance between the entity and the circle center.
	 * @param circleRadius is the radius of the circle.
	 * @param maxRotation is the maximal rotation of the entity.
	 * @param faceBehaviour is the face behaviour to use to face the target point on the circle.
	 */
	public SteeringWanderBehaviour(double circleDistance, double circleRadius, double maxRotation, SteeringFaceBehaviour faceBehaviour) {
		this.circleDistance = circleDistance;
		this.circleRadius = circleRadius;
		this.maxRotation = maxRotation;
		this.rotation = 0.;
		this.faceBehaviour = faceBehaviour;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SteeringBehaviourOutput runWander(Point2d position, Vector2d orientation, double linearSpeed, double maxLinearAcc, double angularSpeed, double maxAngularAcc) {
		Vector2d circleDirection = new Vector2d(orientation);
		circleDirection.scale(this.circleDistance);

		Point2d circleCenter = new Point2d();
		circleCenter.add(position, circleDirection);

		double deltaAngle = (Math.random() - Math.random()) * this.maxRotation;
		this.rotation = GeometryUtil.clamp(this.rotation+deltaAngle, -Math.PI, Math.PI);

		Vector2d targetDirection = new Vector2d(orientation);
		targetDirection.normalize();
		targetDirection.scale(this.circleRadius);
		GeometryUtil.turnVector(targetDirection, this.rotation);

		Point2d faceTarget = new Point2d();
		faceTarget.add(circleCenter, targetDirection);

		SteeringBehaviourOutput output = this.faceBehaviour.runFace(position, orientation, angularSpeed, maxAngularAcc, faceTarget);

		if (output==null) output = new SteeringBehaviourOutput();

		targetDirection = new Vector2d(orientation);
		targetDirection.normalize();
		targetDirection.scale(maxLinearAcc);

		output.setLinear(targetDirection.x, targetDirection.y);

		return output;
	}

}