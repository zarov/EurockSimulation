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

import org.janusproject.kernel.address.AgentAddress;

import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;
import fr.utbm.gi.vi51.g3.framework.tree.QuadTree;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Barrier;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Flora;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Gate;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stage;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stand;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Toilet;

/**
 * State of the world model.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class WorldModelState {

	private final QuadTree worldObjects;
	private final double time;

	WorldModelState(QuadTree wdobjs, double time) {
		this.worldObjects = wdobjs;
		this.time = time;
	}

	public QuadTree getWorldObjects() {
		return this.worldObjects;
	}

	public String getAgentType(SituatedObject o) {
		if (o instanceof AgentBody) {
			AgentBody body = (AgentBody)o;
			AgentAddress adr = body.getOwner();
			if (adr!=null) {
				return adr.getName();
			}
		}
		return null;
	}

	public String getObjectType(SituatedObject o) {
		if (o instanceof Flora){
			return "TREE";
		} else if (o instanceof Gate) {
			return "GATE";
	    } else if(o instanceof Barrier){
			return "BARRIER";
		} else if(o instanceof Stand){
			return ((Stand) o).getName();
		} else if(o instanceof Stage){
			return ((Stage) o).getName();
		} else if(o instanceof Toilet){
			return ((Toilet) o).getName();
		} else {
			return getAgentType(o);
		}
		
	}

	public double getTime() {
		return time;
	}
}