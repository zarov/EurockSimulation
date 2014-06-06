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
import fr.utbm.gi.vi51.g3.motion.agent.NeedType;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Flora;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stage;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stand;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.StandAction;

/**
 * Model of the world.
 *
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class WorldModel extends AbstractEnvironment implements
		WorldModelStateProvider {

	// private MouseTarget mouseTarget = null;

	/**
	 * @param width
	 *            is the width of the world.
	 * @param height
	 *            is the height of the world.
	 */
	public WorldModel(double width, double height) {
		super(width, height, new SimulationTimeManager(500));
		build();
	}

	private void build() {
		// Build stages

		stages();
		stands();
		flora();

	}

	/** Stages **/
	private void stages() {
		Point2d STAGEXY = new Point2d(1150, 650);
		Stage STAGE = new Stage(15, STAGEXY, 15, "Beach");
		implantSituatedObject(STAGE);
	}

	/** Flora **/
	private void flora() {
		/* From top left corner to bottom right corner */
		forest(5, 11, 2, 6);
		forest(20, 680, 3, 4);
		forest(250, 180, 8, 5);
		forest(400, 10, 3, 5);
		forest(650, 680, 3, 5);
		forest(650, 300, 7, 3);
		forest(650, 300, 7, 3);
		forest(950, 80, 5, 4);
		forest(1100, 300, 2, 2);
		forest(1300, 20, 3, 4);
		forest(1300, 200, 2, 14);
		forest(1600, 200, 6, 3);
		forest(1580, 540, 7, 7);
	}

	private void forest(int x, int y, int height, int width) {

		int saveX = x;
		int saveY = y;
		ArrayList<Point2d> trees = new ArrayList<Point2d>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				trees.add(new Point2d(x, y));
				x += 25;
			}
			y += 35;
			x = saveX;
		}
			for(Point2d a : trees)
			{
			Flora tree = new Flora(50, a);
			implantSituatedObject(tree);
			}
	}

	/** Stands **/
	private void stands() {
		graille(180, 20, "food");
		graille(260, 20, "food");
		graille(330, 20, "food");

		graille(400, 250, "food");
		graille(400, 350, "food");

		graille(770, 640, "food");
		graille(815, 690, "food");
		graille(870, 730, "food");

		graille(750, 480, "food");
		graille(750, 400, "food");
		graille(850, 400, "food");
		graille(950, 400, "food");
		graille(850, 480, "food");
		graille(950, 480, "food");

		graille(1550, 280, "food");
		graille(1550, 350, "food");

		graille(1450, 280, "food");
		graille(1450, 350, "food");
		graille(1350, 280, "food");
		graille(1350, 350, "food");
		graille(1120, 20, "food");
		graille(1220, 20, "food");
	}

	private void graille(int x, int y, String type) {
		Point2d FOODXY = new Point2d(x, y);
		StandAction FOODHunger;

		if (type == "food") {
			FOODHunger = new StandAction(NeedType.HUNGER, 10);
		} else {
			FOODHunger = new StandAction(NeedType.THIRST, 10);
		}
		Stand FOOD = new Stand(15, FOODXY, 15, "FOODSTAND", FOODHunger);
		implantSituatedObject(FOOD);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMouseTarget(Point2d target) {
		// if (target==null) this.mouseTarget = null;
		// else this.mouseTarget = new MouseTarget(target.getX(),
		// target.getY());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldModelState getState() {
		/* Ici ça envoie le bordel */
		return new WorldModelState(cloneWorldObjects());
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
		if (agent != null) {
			double x1 = agent.getX();
			double y1 = agent.getY();

			for (AgentBody b1 : getAgentBodies()) {
				if (b1 != agent) {
					double x2 = b1.getX();
					double y2 = b1.getY();
					double distance = new Vector2d(x2 - x1, y2 - y1).length();
					if (distance < agent.getPerceptionRange()) {
						allPercepts.add(new Perception(b1));
					}
				}
			}
			for (SituatedObject o1 : getOtherObjects()) {
				double x2 = o1.getX();
				double y2 = o1.getY();
				double distance = new Vector2d(x2 - x1, y2 - y1).length();
				if (distance < agent.getPerceptionRange()) {
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
	protected void applyInfluences(Collection<MotionInfluence> influences,
			SimulationTimeManager timeManager) {
		List<MotionInfluence> influenceList = new ArrayList<MotionInfluence>(
				influences);
		List<AnimatAction> actions = new ArrayList<AnimatAction>(
				influenceList.size());

		// Compute actions
		for (int index1 = 0; index1 < influenceList.size(); index1++) {
			MotionInfluence inf1 = influenceList.get(index1);
			AgentBody body1 = getAgentBodyFor(inf1.getEmitter());
			if (body1 != null) {
				Vector2d move;
				double rotation;
				if (inf1.getType() == DynamicType.STEEERING) {
					move = computeSteeringTranslation(body1,
							inf1.getLinearInfluence(), timeManager);
					rotation = computeSteeringRotation(body1,
							inf1.getAngularInfluence(), timeManager);
				} else {
					move = computeKinematicTranslation(body1,
							inf1.getLinearInfluence(), timeManager);
					rotation = computeKinematicRotation(body1,
							inf1.getAngularInfluence(), timeManager);
				}

				double x1 = body1.getX();
				double y1 = body1.getY();

				// Trivial collision detection
				for (int index2 = index1 + 1; index2 < influenceList.size(); index2++) {
					MotionInfluence inf2 = influenceList.get(index2);
					AgentBody body2 = getAgentBodyFor(inf2.getEmitter());
					if (body2 != null) {
						double x2 = body2.getX();
						double y2 = body2.getY();

						double distance = new Vector2d(x2 - x1, y2 - y1)
								.length();

						if (distance < (body1.getSize() + body2.getSize())) {
							move.set(0, 0);
							break;
						}
					}
				}

				actions.add(new AnimatAction(body1, move, rotation));

			}
		}

		// Apply the actions
		for (AnimatAction action : actions) {
			AgentBody body = action.getObjectToMove();
			if (body != null) {
				move(body, action.getTranslation(), action.getRotation());
			}
		}
	}

}