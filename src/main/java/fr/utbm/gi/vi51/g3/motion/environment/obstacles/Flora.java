package fr.utbm.gi.vi51.g3.motion.environment.obstacles;

import javax.vecmath.Point2d;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.AbstractSituatedObject;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;

public class Flora extends AbstractSituatedObject {

	public Flora(double size, Point2d position) {
		super(size, position);
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
