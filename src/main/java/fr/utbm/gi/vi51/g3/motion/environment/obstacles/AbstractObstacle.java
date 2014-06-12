package fr.utbm.gi.vi51.g3.motion.environment.obstacles;

import javax.vecmath.Point2d;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.AbstractSituatedObject;

public class AbstractObstacle extends AbstractSituatedObject {

	public AbstractObstacle(double size, Point2d position, double orientation) {
		super(size, position, orientation);
	}

	public AbstractObstacle(double size, Point2d position) {
		super(size, position);
	}

	/**
	 * There is no box by default (example : Bomb)
	 */
	@Override
	public AABB getBox() {
		return null;
	}

}
