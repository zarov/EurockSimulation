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

/**
 * Object on the environment.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public interface SituatedObject {

	/** Replies the size of the object.
	 * 
	 * @return the size of this object.
	 */
	public double getSize();

	/** Replies the position of the object.
	 * 
	 * @return the x-coordinate of the position of this object.
	 */
	public double getX();
	
	/** Replies the position of the object.
	 * 
	 * @return the y-coordinate of the position of this object.
	 */
	public double getY();

	/** Replies the position of the object.
	 * 
	 * @return the position of the object.
	 */
	public Point2d getPosition();

	/** Replies the orientation of the object.
	 * 
	 * @return the angle of orientation from (1,0).
	 */
	public double getAngle();
	
	/** Replies the orientation of the object.
	 * 
	 * @return the orientation direction.
	 */
	public Vector2d getDirection();

}