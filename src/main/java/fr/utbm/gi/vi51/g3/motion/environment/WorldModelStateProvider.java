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
package fr.utbm.gi.vi51.g3.motion.environment;

import javax.vecmath.Point2d;

/**
 * State provider for the world model.  
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public interface WorldModelStateProvider {

	/** Set the mouse target.
	 * 
	 * @param target
	 */
	public void setMouseTarget(Point2d target);

	/** Replies the width of the environment.
	 * 
	 * @return the width of the environment.
	 */
	public double getWidth();
	
	/** Replies the height of the environment.
	 * 
	 * @return the height of the environment.
	 */
	public double getHeight();
	
	/** Replies the state of the world model.
	 * 
	 * @return the state of the world model.
	 */
	public WorldModelState getState();

}