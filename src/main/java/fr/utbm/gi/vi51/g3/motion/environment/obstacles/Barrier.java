package fr.utbm.gi.vi51.g3.motion.environment.obstacles;

import javax.vecmath.Point2d;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;

public class Barrier extends AbstractObstacle {

	public Barrier(double size, Point2d position) {
		super(size, position);
	}

	@Override
	public AABB getBox() {
		return new AABB(getX() - (getSize() / 2), getX() + (getSize() / 2),
				getY() - (getSize() / 2), getY() + (getSize() / 2));
	}
}
