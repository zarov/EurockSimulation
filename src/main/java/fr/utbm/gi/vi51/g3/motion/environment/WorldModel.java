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
import java.util.List;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import fr.utbm.gi.vi51.g3.framework.environment.AbstractEnvironment;
import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;
import fr.utbm.gi.vi51.g3.framework.environment.DynamicType;
import fr.utbm.gi.vi51.g3.framework.environment.EnvironmentEvent;
import fr.utbm.gi.vi51.g3.framework.environment.MotionInfluence;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;
import fr.utbm.gi.vi51.g3.framework.time.SimulationTimeManager;

/**
 * Model of the world.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class WorldModel extends AbstractEnvironment implements WorldModelStateProvider {

	//	private MouseTarget mouseTarget = null;

	/**
	 * @param width is the width of the world.
	 * @param height is the height of the world.
	 */
	public WorldModel(double width, double height) {
		super(width, height, new SimulationTimeManager(500));
		build();
	}

	private void build(){
		// Build stages

	}

	/** {@inheritDoc}
	 */
	@Override
	public void setMouseTarget(Point2d target) {
		//		if (target==null) this.mouseTarget = null;
		//		else this.mouseTarget = new MouseTarget(target.getX(), target.getY());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldModelState getState() {
		return new WorldModelState(cloneAgentBodies());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EnvironmentEvent createEnvironmentEvent() {
		return new WorldModelEvent(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<Perception> computePerceptionsFor(AgentBody agent) {
		List<Perception> allPercepts = new ArrayList<Perception>();
		if (agent!=null) {
			double x1 = agent.getX();
			double y1 = agent.getY();

			// add mouse target in perceptions
			//			if (this.mouseTarget!=null) {
			//				allPercepts.add(new Perception(this.mouseTarget));
			//			}

			for(AgentBody b1 : getAgentBodies()) {
				if (b1!=agent) {
					double x2 = b1.getX();
					double y2 = b1.getY();
					double distance = new Vector2d(x2-x1,y2-y1).length();
					if (distance<agent.getPerceptionRange()) {
						allPercepts.add(new Perception(b1));
					}
				}
			}
			for(SituatedObject o1 : getOtherObjects()) {
				double x2 = o1.getX();
				double y2 = o1.getY();
				double distance = new Vector2d(x2-x1,y2-y1).length();
				if (distance<agent.getPerceptionRange()) {
					allPercepts.add(new Perception(o1));
				}
			}
		}
		return allPercepts;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void applyInfluences(Collection<MotionInfluence> influences, SimulationTimeManager timeManager) {
		List<MotionInfluence> influenceList = new ArrayList<MotionInfluence>(influences);
		List<AnimatAction> actions = new ArrayList<AnimatAction>(influenceList.size());

		// Compute actions
		for(int index1=0; index1<influenceList.size(); index1++) {
			MotionInfluence inf1 = influenceList.get(index1);
			AgentBody body1 = getAgentBodyFor(inf1.getEmitter());
			if (body1!=null) {
				Vector2d move;
				double rotation;
				if (inf1.getType()==DynamicType.STEEERING) {
					move = computeSteeringTranslation(body1, inf1.getLinearInfluence(), timeManager);
					rotation = computeSteeringRotation(body1, inf1.getAngularInfluence(), timeManager);
				}
				else {
					move = computeKinematicTranslation(body1, inf1.getLinearInfluence(), timeManager);
					rotation = computeKinematicRotation(body1, inf1.getAngularInfluence(), timeManager);
				}

				double x1 = body1.getX();
				double y1 = body1.getY();

				// Trivial collision detection
				for(int index2=index1+1; index2<influenceList.size(); index2++) {
					MotionInfluence inf2 = influenceList.get(index2);
					AgentBody body2 = getAgentBodyFor(inf2.getEmitter());
					if (body2!=null) {
						double x2 = body2.getX();
						double y2 = body2.getY();

						double distance = new Vector2d(x2-x1,y2-y1).length();

						if (distance<(body1.getSize()+body2.getSize())) {
							move.set(0,0);
							break;
						}
					}
				}

				actions.add(new AnimatAction(body1, move, rotation));

			}
		}

		// Apply the actions
		for(AnimatAction action : actions) {
			AgentBody body = action.getObjectToMove();
			if (body!=null) {
				move(
						body,
						action.getTranslation(),
						action.getRotation());
			}
		}
	}

}