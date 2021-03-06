/*
 * $Id$
 *
 * Copyright (c) 2007-13 Stephane GALLAND.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.utbm.gi.vi51.g3.framework.environment;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import fr.utbm.gi.vi51.g3.framework.util.GeometryUtil;

/**
 * Abstract implementation of an object on the environment.
 *
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class AbstractSituatedObject implements SituatedObject {

	private final double size;
	private final Point2d position = new Point2d();
	private double angle = 0;

	/**
	 * @param size
	 *            of the object.
	 */
	public AbstractSituatedObject(double size) {
		this.size = size;
	}

	/**
	 * @param size
	 *            of the object.
	 * @param position
	 *            is the position of the object.
	 * @param orientation
	 *            is the orientation of the object.
	 */
	public AbstractSituatedObject(double size, Point2d position,
			double orientation) {
		assert (position != null);
		this.size = size;
		this.position.set(position);
		angle = orientation;
	}

	/**
	 * @param size
	 *            of the object.
	 * @param x
	 *            is the position of the object.
	 * @param y
	 *            is the position of the object.
	 * @param orientation
	 *            is the orientation of the object.
	 */
	public AbstractSituatedObject(double size, double x, double y,
			double orientation) {
		this.size = size;
		position.set(x, y);
		angle = orientation;
	}

	/**
	 * @param size
	 *            of the object.
	 * @param position
	 *            is the position of the object.
	 */
	public AbstractSituatedObject(double size, Point2d position) {
		assert (position != null);
		this.size = size;
		this.position.set(position);
	}

	/**
	 * @param size
	 *            of the object.
	 * @param x
	 *            is the position of the object.
	 * @param y
	 *            is the position of the object.
	 */
	public AbstractSituatedObject(double size, double x, double y) {
		this.size = size;
		position.set(x, y);
	}

	/**
	 * @param size
	 *            of the object.
	 * @param orientation
	 *            is the orientation of the object.
	 */
	public AbstractSituatedObject(double size, double orientation) {
		this.size = size;
		angle = orientation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getSize() {
		return size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Point2d getPosition() {
		return new Point2d(position);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getX() {
		return position.getX();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getY() {
		return position.getY();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getAngle() {
		return angle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2d getDirection() {
		return GeometryUtil.toOrientationVector(angle);
	}

	/**
	 * Set the orientation of the object.
	 *
	 * @param angle
	 */
	protected void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * Set the direction of the object.
	 *
	 * @param dx
	 * @param dy
	 */
	protected void setDirection(double dx, double dy) {
		angle = GeometryUtil.toOrientationAngle(new Vector2d(dx, dy));
	}

	/**
	 * Set the position of the object.
	 *
	 * @param x
	 * @param y
	 */
	protected void setPosition(double x, double y) {
		position.set(x, y);
	}

	/**
	 * Move the situated object.
	 *
	 * @param dx
	 *            is the real instant motion.
	 * @param dy
	 *            is the real instant motion.
	 * @param simulationDuration
	 *            is the time during which the motion is applied.
	 * @param worldWidth
	 *            is the width of the world.
	 * @param worldHeight
	 *            is the height of the world.
	 * @return the real motion.
	 */
	Vector2d move(double dx, double dy, double simulationDuration,
			double worldWidth, double worldHeight) {
		Vector2d r = new Vector2d(dx, dy);
		double tx = position.x + dx;
		double ty = position.y + dy;

		double size = getSize();

		if (tx < size) {
			r.setX(size - position.x);
			tx = size;
		} else if (tx > (worldWidth - size)) {
			r.setX(worldWidth - size - position.x);
			tx = worldWidth - size;
		}

		if (ty < size) {
			r.setY(size - position.y);
			ty = size;
		} else if (ty > (worldHeight - size)) {
			r.setY(worldHeight - size - position.y);
			ty = worldHeight - size;
		}

		position.set(tx, ty);

		return r;
	}

	/**
	 * Rotate the object.
	 *
	 * @param rotation
	 *            is the real instant motion.
	 * @param simulationDuration
	 *            is the time during which the motion is applied.
	 */
	void rotate(double rotation, double simulationDuration) {
		angle += rotation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Perception toPerception() {
		return new Perception(this);
	}

}