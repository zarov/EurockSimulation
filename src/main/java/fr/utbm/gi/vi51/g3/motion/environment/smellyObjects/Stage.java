package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import javax.vecmath.Point2d;

public class Stage extends AbstractSmellyObject {

	private boolean onAir;

	public Stage(double size, Point2d position, double orientation, String name) {
		super(size, position, orientation, name);
		this.onAir = false;
	}

	public boolean isOnAir() {
		return this.onAir;
	}
}
