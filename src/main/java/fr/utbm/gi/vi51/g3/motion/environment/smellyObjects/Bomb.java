/**
 *
 */
package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import javax.vecmath.Point2d;

/**
 * @author zarov
 *
 */
public class Bomb extends AbstractSmellyObject {

	private final int timeBeforeExplosion;

	public Bomb(double size, Point2d position, double orientation, String name,
			int timerValue) {
		super(size, position, orientation, name);
		this.timeBeforeExplosion = timerValue;
	}

	public int getTimeBeforeExplosion() {
		return timeBeforeExplosion;
	}

}
