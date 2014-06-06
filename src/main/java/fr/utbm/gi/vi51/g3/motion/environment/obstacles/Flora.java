package fr.utbm.gi.vi51.g3.motion.environment.obstacles;

import javax.vecmath.Point2d;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.AbstractSituatedObject;

public class Flora extends AbstractSituatedObject {

	public Flora(double size, Point2d position) {
		super(size, position);
	}

	/**
	 * Returns the box containing the flora. By default it is a square, with a
	 * width of {@code size}, and his center is {@code position}.
	 */
	@Override
	public AABB getFrustrum() {
		return new AABB(getX() - (getSize() / 2), getX() + (getSize() / 2),
				getY() - (getSize() / 2), getY() + (getSize() / 2));
	}
}
