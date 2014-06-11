package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import javax.vecmath.Point2d;

public class Stage extends AbstractSmellyObject {
	private final String name;
	private boolean onAir;
	private final double noiseRange = 200;

	public Stage(double size, Point2d position, double orientation, String name) {
		super(size, position, orientation);
		this.name = name;
		this.onAir = false;
	}

	public boolean isOnAir() {
		return this.onAir;
	}
	
	public double getRange(){
		return this.noiseRange;
	}
	
	public boolean isInRange(Point2d position){
		double maxx;
		//compute x max perception sound
		double maxy;
		//compute y max perception sound
		double minx;
		//compute x min perception sound
		double miny;
		//compute y min perception sound
		
		return false;
	}
	
	public boolean isOnAir() {
		return this.onAir;
	}
}
