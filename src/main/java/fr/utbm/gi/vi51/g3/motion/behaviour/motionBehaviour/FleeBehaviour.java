package fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour;

import javax.vecmath.Point2d;

public interface FleeBehaviour<OUT extends BehaviourOutput> {
	
	public OUT runFlee(Point2d position, double linearSpeed, double maxLinear, Point2d target);
	
}
