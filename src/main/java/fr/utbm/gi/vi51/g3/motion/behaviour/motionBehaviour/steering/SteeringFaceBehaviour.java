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
package fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.FaceBehaviour;

/**
 * Steering Face Behaviour.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class SteeringFaceBehaviour implements FaceBehaviour<SteeringBehaviourOutput> {

	private final double ignoreDistance;
	private final SteeringAlignBehaviour alignBehaviour;

	/**
	 * @param ignoreDistance is the distance to the target under which the face behaviour is disable.
	 * @param alignBehaviour is the align behaviour to use to face to.
	 */
	public SteeringFaceBehaviour(double ignoreDistance, SteeringAlignBehaviour alignBehaviour) {
		this.ignoreDistance = Math.abs(ignoreDistance);
		this.alignBehaviour = alignBehaviour;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SteeringBehaviourOutput runFace(Point2d position, Vector2d orientation, double angularSpeed, double maxAngularAcc, Point2d target) {
		Vector2d alignTarget = new Vector2d();
		alignTarget.sub(target, position);
		if (alignTarget.length()<this.ignoreDistance)
			return this.alignBehaviour.runAlign(orientation, angularSpeed, maxAngularAcc, orientation);
		return this.alignBehaviour.runAlign(orientation, angularSpeed, maxAngularAcc, alignTarget);
	}

}