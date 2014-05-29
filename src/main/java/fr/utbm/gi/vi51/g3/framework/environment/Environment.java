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

import java.util.Collection;

import org.janusproject.kernel.address.AgentAddress;

import fr.utbm.gi.vi51.g3.framework.time.SimulationTimeManager;

/**
 * Situated environment.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public interface Environment {

	/** Replies the time manager of this environment.
	 * 
	 * @return the time manager of this environment.
	 */
	public SimulationTimeManager getTimeManager();

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

	/** Replies the bodies in the environment.
	 * 
	 * @return the bodies in the environment.
	 */
	public Collection<AgentBody> getAgentBodies();

	/** Replies the agent body associated to the given agent.
	 * 
	 * @param agentId
	 * @return the agent body or <code>null</code>.
	 */
	public AgentBody getAgentBodyFor(AgentAddress agentId);

	/** Replies the objects in the environment that are not bodies.
	 * 
	 * @return the objects in the environment that are not bodies.
	 */
	public Collection<SituatedObject> getOtherObjects();

	/** Run the environment behaviour: apply influences, compute perceptions.
	 */
	public void runBehaviour();

	/** Add listener on environment events.
	 * 
	 * @param listener
	 */
	public void addEnvironmentListener(EnvironmentListener listener);

	/** Remove listener on environment events.
	 * 
	 * @param listener
	 */
	public void removeEnvironmentListener(EnvironmentListener listener);

	/** Spawn an body for the given agent.
	 * 
	 * @param animat
	 */
	public void spawnAgentBody(Animat<?> animat);

	/** Remove the body for the given agent.
	 * 
	 * @param animat
	 */
	public void killAgentBody(AgentAddress animat);

}