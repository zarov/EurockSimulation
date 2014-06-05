package fr.utbm.gi.vi51.g3.motion.RelevantSpot;

import javax.vecmath.Point2d;

import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.StandAction;

public class Stage extends AbstractRelevantSpot {
	private final String name;
	private boolean OnAir;
	
	public Stage(double size, Point2d position, double orientation, String name) {
		super(size, position, orientation);
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
