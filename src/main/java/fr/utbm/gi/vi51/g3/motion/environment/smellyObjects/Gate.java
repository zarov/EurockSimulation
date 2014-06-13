package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import javax.vecmath.Point2d;

public class Gate extends AbstractSmellyObject {

	public static Point2d staticPosition;

	double killRange = 50;
	public Gate(double size, Point2d position, double orientation, String name) {
		super(size, position, orientation, name);
		staticPosition = position;
	}

}
