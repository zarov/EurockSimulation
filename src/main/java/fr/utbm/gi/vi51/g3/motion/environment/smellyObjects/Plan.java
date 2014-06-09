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
	MAIN("Main", 15, 0, 50, 15), BEACH("Beach", 15, 1150, 650, 15), LOGGIA(
			"Loggia", 15, 0, 100, 15), GREEN("Greenroom", 15, 0, 99, 15);

	public final String name;
	public final int size;
	public final Point2d position;
	public final int direction;

	private Plan(String name, int size, int x, int y, int direction) {
		this.name = name;
		this.size = size;
		position = new Point2d(x, y);
		this.direction = direction;
	}

	public String getName() {
		return name;
	}

	public Point2d getPosition() {
		return position;
	}

	public int getSize() {
		return size;
	}

	public int getDirection() {
		return direction;
	}
}
