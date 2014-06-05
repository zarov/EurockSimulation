package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import javax.vecmath.Point2d;

public class Stage extends AbstractSmellyObject {
	private final String name;
	private boolean OnAir;

	public Stage(double size, Point2d position, double orientation, String name) {
		super(size, position, orientation);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
