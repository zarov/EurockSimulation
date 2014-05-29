package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import javax.vecmath.Point2d;

public class Stand extends AbstractSmellyObject {

	private final String name;
	//TODO enum pour les types de stand ?
	private final StandAction action;

	public Stand(double size, Point2d position, double orientation, String name, StandAction action) {
		super(size, position, orientation);
		this.action = action;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public StandAction getAction() {
		return this.action;
	}

}
