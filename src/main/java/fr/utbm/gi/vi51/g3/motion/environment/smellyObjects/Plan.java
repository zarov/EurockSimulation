/**
 *
 */
package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import javax.vecmath.Point2d;

/**
 * List all the smelly objects (Stage) and their position.
 *
 * @author zarov
 *
 */
public enum Plan {
	MAIN("Main", 100, 300, 50, 350, 15), BEACH("Beach", 220, 100, 1260, 700, 15), LOGGIA(
			"Loggia", 250, 100, 200, 660, 15), GREEN("Greenroom", 300, 100,
			700, 55, 15);

	public final String name;
	public final int sizeX;
	public final int sizeY;
	public final Point2d position;
	public final int direction;

	private Plan(String name, int sizeX, int sizeY, int x, int y, int direction) {
		this.name = name;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		position = new Point2d(x, y);
		this.direction = direction;
	}

	public String getName() {
		return name;
	}

	public Point2d getPosition() {
		return position;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public int getDirection() {
		return direction;
	}
}
