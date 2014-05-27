package fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.FleeBehaviour;

public class SteeringFleeBehaviour implements FleeBehaviour<SteeringBehaviourOutput> {

	@Override
	public SteeringBehaviourOutput runFlee(Point2d position, double linearSpeed,
			double maxLinearAcc, Point2d target) {
		SteeringBehaviourOutput output = new SteeringBehaviourOutput();

		Vector2d direction = new Vector2d();
		direction.sub(position,target);

		direction.normalize();
		direction.scale(maxLinearAcc);

		output.setLinear(direction.getX(), direction.getY());

		return output;
	}

}
