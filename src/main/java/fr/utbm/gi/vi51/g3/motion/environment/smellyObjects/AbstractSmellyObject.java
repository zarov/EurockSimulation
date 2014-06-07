package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import javax.vecmath.Point2d;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.AbstractSituatedObject;

public class AbstractSmellyObject extends AbstractSituatedObject {

	protected String name;

	public AbstractSmellyObject(double size, Point2d position,
			double orientation, String name) {
		super(size, position, orientation);
		this.name = name;
	}

	@Override
	public AABB getFrustrum() {
		return new AABB(getX() - (getSize() / 2), getX() + (getSize() / 2),
				getY() - (getSize() / 2), getY() + (getSize() / 2));
	}

	public String getName() {
		return this.name;
	}
}
