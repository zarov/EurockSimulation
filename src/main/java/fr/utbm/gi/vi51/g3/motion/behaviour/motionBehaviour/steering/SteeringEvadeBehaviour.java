package fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.EvadeBehaviour;

public class SteeringEvadeBehaviour implements EvadeBehaviour<SteeringBehaviourOutput>{

	@Override
	public SteeringBehaviourOutput runEvade(Point2d position,
			double linearSpeed, double maxLinear, Point2d pursuedTarget,
			double targetSpeed, Vector2d targetOrientation) {

		double prediction;
		SteeringFleeBehaviour steeringFleeBehaviour = new SteeringFleeBehaviour();

		// Find out the future position of the agent to run away from
		Point2d newTarget = new Point2d(pursuedTarget);

		Vector2d direction = new Vector2d();
		direction.sub(pursuedTarget,position);

		if(linearSpeed < direction.length()/MAX_PREDICTION){
			prediction = MAX_PREDICTION;
		} else {
			prediction = direction.length()/linearSpeed;
		}
		targetOrientation.normalize();
		targetOrientation.scale(prediction);
		newTarget.add(targetOrientation);

		// Delegate to flee
		return steeringFleeBehaviour.runFlee(position, linearSpeed, maxLinear, newTarget);
	}

}
