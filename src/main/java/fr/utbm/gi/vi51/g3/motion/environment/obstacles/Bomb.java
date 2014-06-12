/**
 *
 */
package fr.utbm.gi.vi51.g3.motion.environment.obstacles;

import javax.vecmath.Point2d;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.AbstractSituatedObject;

/**
 * @author zarov
 *
 */
public class Bomb extends AbstractSituatedObject {

	private final int timeBeforeExplosion;

	public Bomb(double size, Point2d position, double orientation, String name,
			int timerValue) {
		super(size, position, orientation);
		this.timeBeforeExplosion = timerValue;
	}

	public int getTimeBeforeExplosion() {
		return timeBeforeExplosion;
	}

	@Override
	public AABB getFrustrum() {
		// TODO Auto-generated method stub
		return null;
	}

}
