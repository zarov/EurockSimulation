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
import java.util.Set;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import org.janusproject.kernel.address.AgentAddress;

import fr.utbm.gi.vi51.g3.framework.environment.AbstractEnvironment;
import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;
import fr.utbm.gi.vi51.g3.framework.environment.DynamicType;
import fr.utbm.gi.vi51.g3.framework.environment.EnvironmentEvent;
import fr.utbm.gi.vi51.g3.framework.environment.MotionInfluence;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;
import fr.utbm.gi.vi51.g3.framework.time.SimulationTimeManager;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Flora;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Gate;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stage;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stand;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Toilet;

/**
 * Model of the world.
 *
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class WorldModel extends AbstractEnvironment implements
		WorldModelStateProvider {

	/**
	 * @param width
	 *            is the width of the world.
	 * @param height
	 *            is the height of the world.
	 */
	public WorldModel(double width, double height, Set<Stand> standSet,
			Set<Toilet> toiletSet, Set<Stage> stageSet) {
		super(width, height, new SimulationTimeManager(500));
		build(standSet, toiletSet, stageSet);
	}

	private void build(Set<Stand> standSet, Set<Toilet> toiletSet,
			Set<Stage> stageSet) {

		buildStages(stageSet);
		buildStands(standSet);
		buildFlora();
		buildBathrooms(toiletSet);
		buildBarriers();

		Point2d a = new Point2d(1780, 50);
		Gate g = new Gate(15, a, 15, "Entry");
		implantSituatedObject(g);

	}

	private void buildBarriers() {
		// TODO appel de gate() fais planter le QuadTree, pourquoi ?
		// gate(120, 210, 17, 1, 15);
		// gate(145, 210, 1, 7, 15);

	}

	// private void gate(int x, int y, int height, int width, int size) {
	//
	// System.out.println("Building gates ...");
	// int saveX = x;
	// ArrayList<Point2d> gate = new ArrayList<Point2d>();
	//
	// for (int i = 0; i < height; i++) {
	// for (int j = 0; j < width; j++) {
	// gate.add(new Point2d(x, y));
	// x += 25;
	// }
	//
	// y += 35;
	// x = saveX;
	// }
	//
	// for (Point2d a : gate) {
	// Barrier b = new Barrier(size, a);
	// implantSituatedObject(b);
	// }
	// }

	private void buildBathrooms(Set<Toilet> toiletSet) {

		System.out.println("Building bathrooms ...");

		for (Toilet elem : toiletSet) {
			implantSituatedObject(elem);
		}
		// AttendantGender male = AttendantGender.MAN;
		// AttendantGender female = AttendantGender.WOMAN;
		//
		// setBathrooms(5, 90, male);
		// setBathrooms(5, 150, female);
		//
		// setBathrooms(500, 740, male);
		// setBathrooms(580, 740, female);
		//
		// setBathrooms(980, 690, male);
		// setBathrooms(1050, 740, female);
		//
		// setBathrooms(900, 20, male);
		// setBathrooms(980, 20, female);
		//
		// setBathrooms(1420, 740, male);
		// setBathrooms(1500, 740, female);
		//
		// setBathrooms(1700, 420, male);
		// setBathrooms(1700, 490, female);
	}

	// private void setBathrooms(int x, int y, AttendantGender a) {
	// Set<StandAction> actions = new HashSet<StandAction>();
	// actions.add(new StandAction(NeedType.PEE, -6));
	//
	// Point2d BathR = new Point2d(x, y);
	// Toilet B = new Toilet(15, BathR, 15, "TOILET", a, actions);
	// implantSituatedObject(B);
	// }

	private void buildStages(Set<Stage> stageSet) {

		System.out.println("Building stages ...");
		for (Stage elem : stageSet) {
			implantSituatedObject(elem);
		}
	}

	private void buildFlora() {

		System.out.println("Building trees ...");
		/* From top left corner to bottom right corner */
		forest(5, 11, 2, 6);
		forest(20, 680, 3, 4);
		// forest(250, 180, 8, 5); //TODO plante le quadtree
		forest(400, 10, 3, 5);
		forest(650, 680, 3, 5);
		forest(650, 300, 7, 3);
		forest(950, 80, 5, 4);
		forest(1100, 300, 2, 2);
		forest(1300, 20, 3, 4);
		forest(1300, 200, 2, 14);
		// forest(1600, 200, 6, 3); // TODO plante le quadtree
		forest(1580, 540, 7, 7);
	}

	private void forest(int x, int y, int height, int width) {
		int saveX = x;
		ArrayList<Point2d> trees = new ArrayList<Point2d>();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				trees.add(new Point2d(x, y));
				x += 25;
			}

			y += 35;
			x = saveX;
		}

		for (Point2d a : trees) {
			Flora tree = new Flora(50, a);
			implantSituatedObject(tree);
		}
	}

	private void buildStands(Set<Stand> standSet) {

		System.out.println("Building stands");
		for (Stand elem : standSet) {
			implantSituatedObject(elem);
		}
		// graille(180, 20, "food");
		// graille(260, 20, "food");
		// graille(330, 20, "food");
		//
		// // graille(400, 250, "food");
		// // graille(400, 350, "food");
		//
		// graille(770, 640, "food");
		// graille(815, 690, "food");
		// graille(870, 730, "food");
		//
		// graille(750, 480, "food");
		// graille(750, 400, "food");
		// graille(850, 400, "food");
		// graille(950, 400, "food");
		// graille(850, 480, "food");
		// graille(950, 480, "food");
		//
		// graille(1550, 280, "food");
		// graille(1550, 350, "food");
		//
		// graille(1450, 280, "food");
		// graille(1450, 350, "food");
		// graille(1350, 280, "food");
		// graille(1350, 350, "food");
		// graille(1120, 20, "food");
		// graille(1220, 20, "food");
	}

	// private void graille(int x, int y, String type) {
	// Point2d FOODXY = new Point2d(x, y);
	// StandAction FOODHunger;
	//
	// if (type == "food") {
	// FOODHunger = new StandAction(NeedType.HUNGER, 10);
	// } else {
	// FOODHunger = new StandAction(NeedType.THIRST, 10);
	// }
	// Stand FOOD = new Stand(15, FOODXY, 15, "FOODSTAND", FOODHunger);
	// implantSituatedObject(FOOD);
	// }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorldModelState getState() {
		return new WorldModelState(cloneWorldObjects(), getCurrentTime());
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
	protected List<Perception> computePerceptionsFor(AgentBody body) {
		List<Perception> perceptions = null;

		if (body != null) {
			perceptions = getState().getWorldObjects().cull(body.getFrustrum());
			if (bomb != null) {
				perceptions.add(bomb.toPerception());
			}
		}

		return perceptions;
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
			AgentBody body1 = (AgentBody) inf1.getInfluencedObject();

			if (body1 != null) {
				// Remove the body from the QuadTree
				worldObjects.remove(body1);
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
				// Add the body from the QuadTree
				worldObjects.insert(body);
			}
		}
	}

	@Override
	public double getCurrentTime() {
		return getTimeManager().getCurrentTime();
	}

	@Override
	public void killAgentBody(AgentAddress agent) {
		// TODO Auto-generated method stub

	}
}