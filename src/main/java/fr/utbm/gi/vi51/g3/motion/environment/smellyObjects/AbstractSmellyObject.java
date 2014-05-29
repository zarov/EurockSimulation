package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import javax.vecmath.Point2d;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.AbstractSituatedObject;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;

public class AbstractSmellyObject extends AbstractSituatedObject {

	public AbstractSmellyObject(double size) {
		super(size);
		// TODO Auto-generated constructor stub
	}

	public AbstractSmellyObject(double size, Point2d position,
			double orientation) {
		super(size, position, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Perception toPerception() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AABB getBox() {
		// TODO Auto-generated method stub
		return null;
	}
}
