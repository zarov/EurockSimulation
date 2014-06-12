package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import java.util.Set;

import javax.vecmath.Point2d;

public class Stand extends AbstractSmellyObject {

	public Stand(double size, Point2d position, double orientation,
			String name, Set<StandAction> actions) {
		super(size, position, orientation, name, actions);
	}

}
