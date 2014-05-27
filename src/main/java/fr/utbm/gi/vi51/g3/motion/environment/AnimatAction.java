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

import javax.vecmath.Vector2d;

import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;

/**
 * Real action to apply in the world.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
class AnimatAction {

	private final AgentBody body;
	private final Vector2d move;
	private final double rotation;

	/**
	 * @param object is the animat body.
	 * @param move is the translation.
	 * @param rotation is the rotation.
	 */
	public AnimatAction(AgentBody object, Vector2d move, double rotation) {
		this.body = object;
		this.move = move;
		this.rotation = rotation;
	}

	/** Replies the moved object.
	 * 
	 * @return the moved object.
	 */
	public AgentBody getObjectToMove() {
		return this.body;
	}

	/** Replies the translation.
	 * 
	 * @return the translation.
	 */
	public Vector2d getTranslation() {
		return this.move;
	}

	/** Replies the rotation.
	 * 
	 * @return the rotation.
	 */
	public double getRotation() {
		return this.rotation;
	}

}