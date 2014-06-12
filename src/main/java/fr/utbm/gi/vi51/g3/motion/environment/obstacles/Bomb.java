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

	private double timeBeforeExplosion;
	private double lastTime;
	private final int rangeKill = 100;
	private final int rangeHurt = 200;

	public Bomb(double size, Point2d position, double orientation, String name,
			int timerValue, double initTime) {
		super(size, position, orientation);
		this.timeBeforeExplosion = timerValue;
		lastTime = initTime;
	}

	public double getTimeBeforeExplosion() {
		return timeBeforeExplosion;
	}
	
	public void decreseBomb(double time) {
		this.timeBeforeExplosion-=(time - lastTime)/5000;
		this.lastTime = time;
		if(this.timeBeforeExplosion <=0)
			this.timeBeforeExplosion = 0;
	}

	@Override
	public AABB getFrustrum() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRangeKill() {
		return rangeKill;
	}

	public int getRangeHurt() {
		return rangeHurt;
	}

}
