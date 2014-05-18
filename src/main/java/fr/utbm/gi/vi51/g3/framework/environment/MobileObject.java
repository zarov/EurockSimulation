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

import javax.vecmath.Vector2d;

/**
 * Object on the environment.
 *
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public interface MobileObject extends WorldObject {

	/**
	 * Replies the max linear speed.
	 *
	 * @return the max linear speed.
	 */
	public double getMaxLinearSpeed();

	/**
	 * Replies the max angular speed.
	 *
	 * @return the max angular speed.
	 */
	public double getMaxAngularSpeed();

	/**
	 * Replies the max linear acceleration.
	 *
	 * @return the max linear acceleration.
	 */
	public double getMaxLinearAcceleration();

	/**
	 * Replies the max angular acceleration.
	 *
	 * @return the max angular acceleration.
	 */
	public double getMaxAngularAcceleration();

	/**
	 * Replies the current angular speed.
	 *
	 * @return the current angular speed.
	 */
	public double getCurrentAngularSpeed();

	/**
	 * Replies the current linear speed.
	 *
	 * @return the current linear speed.
	 */
	public double getCurrentLinearSpeed();

	/**
	 * Replies the current linear motion.
	 *
	 * @return the current linear motion.
	 */
	public Vector2d getCurrentLinearMotion();

}