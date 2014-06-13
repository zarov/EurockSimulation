/**
 *
 */
package fr.utbm.gi.vi51.g3.motion.environment.obstacles;

import javax.vecmath.Point2d;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;

/**
 * @author zarov
 *
 */
public class Bomb extends AbstractObstacle {

	private double timeBeforeExplosion;
	private double lastTime;
	private final int rangeKill = 100;
	private final int rangeHurt = 200;
	private final AABB Box;

	public Bomb(double size, Point2d position, double orientation, String name,
			int timerValue, double initTime) {
		super(size, position, orientation);
		timeBeforeExplosion = timerValue;
		lastTime = initTime;
		this.Box = new AABB(position.x-rangeKill/2, position.x+rangeKill/2, position.y-rangeKill/2,position.y+rangeKill/2);
	}

	public double getTimeBeforeExplosion() {
		return timeBeforeExplosion;
	}

	public void decreseBomb(double time) {
		timeBeforeExplosion -= (time - lastTime) / 5000;
		lastTime = time;
		if (timeBeforeExplosion <= 0) {
			timeBeforeExplosion = 0;
		}
	}

	public int getRangeKill() {
		return rangeKill;
	}

	public int getRangeHurt() {
		return rangeHurt;
	}
}
