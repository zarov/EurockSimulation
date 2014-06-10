package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import javax.vecmath.Point2d;

public class Stage extends AbstractSmellyObject {

	private final boolean onAir;
	private final double noiseRange = 200;
	private final int sizeX;
	private final int sizeY;

	public Stage(Point2d position, double orientation, String name, int sizeX,
			int sizeY) {
		super(sizeX, position, orientation, name);
		onAir = false;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public double getRange() {
		return noiseRange;
	}

	public boolean isInRange(Point2d position) {
		double maxx;
		// compute x max perception sound
		double maxy;
		// compute y max perception sound
		double minx;
		// compute x min perception sound
		double miny;
		// compute y min perception sound

		return false;
	}

	public boolean isOnAir() {
		return onAir;
	}

	/**
	 * @return the sizeX
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * @return the sizeY
	 */
	public int getSizeY() {
		return sizeY;
	}
}
