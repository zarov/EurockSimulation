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

import fr.utbm.gi.vi51.g3.framework.environment.DynamicType;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.BehaviourOutput;

/**
 * Output for a steering behaviour.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public final class SteeringBehaviourOutput extends BehaviourOutput {

	/**
	 */
	public SteeringBehaviourOutput() {
		super(DynamicType.STEEERING);
	}

	/**
	 * @param outputToCopy is the output to copy.
	 */
	public SteeringBehaviourOutput(SteeringBehaviourOutput outputToCopy) {
		super(outputToCopy);
	}

}