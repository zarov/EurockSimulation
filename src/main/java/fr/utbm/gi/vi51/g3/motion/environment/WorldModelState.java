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

import java.util.ArrayList;
import java.util.Collection;

import org.janusproject.kernel.address.AgentAddress;

import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Barrier;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Flora;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stand;

/**
 * State of the world model.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class WorldModelState {

	private final Collection<? extends SituatedObject> objects;
	private final Collection<? extends SituatedObject> agents;

	
	WorldModelState(Collection<? extends SituatedObject> objs) {
		this.objects = new ArrayList<SituatedObject>(objs);
		this.agents = new ArrayList<SituatedObject>();
	}
	
	WorldModelState(Collection<? extends SituatedObject> agents, Collection<? extends SituatedObject> objet) {
		this.agents = new ArrayList<SituatedObject>(agents);
		this.objects = new ArrayList<SituatedObject>(objet);
	}
  
	public Collection <? extends SituatedObject> getAgents() {
		return this.agents;
	}
	public Collection<? extends SituatedObject> getObjects() {
		return this.objects;
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
		} else if(o instanceof Barrier){
			return "BARRIER";
		} else if(o instanceof Stand){
			return ((Stand) o).getName();
		}
		return null;
	}
}