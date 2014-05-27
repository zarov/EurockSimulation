package fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

public interface EvadeBehaviour<OUT extends BehaviourOutput>  {

	static final double MAX_PREDICTION = 2;

	public OUT runEvade(Point2d position, double linearSpeed, double maxLinear,  Point2d pursuedTarget, double targetSpeed, Vector2d targetOrientation);
}
