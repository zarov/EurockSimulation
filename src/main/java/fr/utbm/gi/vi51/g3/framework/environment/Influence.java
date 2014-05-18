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

import org.janusproject.kernel.address.AgentAddress;

/**
 * Abstract implementation of an influence.
 * 
 * @param <IO> is the type of the influencable objects.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public abstract class Influence<IO extends AbstractWorldObject> {

	private AgentAddress emitter = null;
	private final IO influencedObject;
	
	/**
	 * @param influencedObject is the influenced object.
	 */
	protected Influence(IO influencedObject) {
		this.influencedObject = influencedObject;
	}
	
	/** Replies the emitter of the influence.
	 * 
	 * @return the emitter of the influence.
	 */
	public AgentAddress getEmitter() {
		return this.emitter;
	}
	
	/** Set the emitter of the influence.
	 * 
	 * @param emitter is the emitter of the influence.
	 */
	void setEmitter(AgentAddress emitter) {
		assert(emitter!=null);
		this.emitter = emitter;
	}

	/** Replies the influenced object.
	 * 
	 * @return the influenced object.
	 */
	public IO getInfluencedObject() {
		return this.influencedObject;
	}
	
}