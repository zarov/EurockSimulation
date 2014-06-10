package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import javax.vecmath.Point2d;

public class Stage extends AbstractSmellyObject {

	private final boolean onAir;
	private final double noiseRange = 200;
	private final int width;
	private final int height;

	public Stage(Point2d position, double orientation, String name, int width,
			int height) {
		super(width, position, orientation, name);
		onAir = false;
		this.width = width;
		this.height = height;
	}

	public double getRange() {
		return noiseRange;
	}

	public boolean isInRange(Point2d position) {
		// double maxx;
		// // compute x max perception sound
		// double maxy;
		// // compute y max perception sound
		// double minx;
		// // compute x min perception sound
		// double miny;
		// // compute y min perception sound

		return false;
	}

	public boolean isOnAir() {
		return onAir;
	}

	/**
	 * @return the sizeX
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the sizeY
	 */
	public int getHeight() {
		return height;
	}
}
