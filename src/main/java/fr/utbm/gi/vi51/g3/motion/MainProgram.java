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
package fr.utbm.gi.vi51.g3.motion;

import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Point2d;

import fr.utbm.gi.vi51.g3.framework.FrameworkLauncher;
import fr.utbm.gi.vi51.g3.framework.environment.Environment;
import fr.utbm.gi.vi51.g3.framework.gui.FrameworkGUI;
import fr.utbm.gi.vi51.g3.motion.agent.Attendant;
import fr.utbm.gi.vi51.g3.motion.agent.AttendantGender;
import fr.utbm.gi.vi51.g3.motion.agent.DamePipi;
import fr.utbm.gi.vi51.g3.motion.agent.Seller;
import fr.utbm.gi.vi51.g3.motion.behaviour.decisionBehaviour.NeedType;
import fr.utbm.gi.vi51.g3.motion.environment.WorldModel;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stand;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.StandAction;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Toilet;
import fr.utbm.gi.vi51.g3.motion.gui.GUI;

public class MainProgram {

	private static double WORLD_SIZE_X = 1780;
	private static double WORLD_SIZE_Y = 780;
	private static long EXECUTION_DELAY = 50;

	/**
	 * Main program.
	 *
	 * @param argv
	 *            are the command line arguments.
	 */
	public static void main(String[] argv) {

		Set<Stand> standSet = initStands();
		Set<Toilet> toiletSet = initToilets();

		System.out.println("--- GUI initialization");
		FrameworkGUI gui = new GUI(WORLD_SIZE_X, WORLD_SIZE_Y);

		System.out.println("--- Environment initialization");
		Environment environment = new WorldModel(WORLD_SIZE_X, WORLD_SIZE_Y,
				standSet, toiletSet);

		FrameworkLauncher.launchEnvironment(environment, gui, EXECUTION_DELAY);

		System.out.println("Creating Agents ...");

		for (int i = 0; i < 100; i++) {
			FrameworkLauncher.launchAgent(new Attendant(AttendantGender.MAN));
			FrameworkLauncher.launchAgent(new Attendant(AttendantGender.WOMAN));
		}

		for (Stand elem : standSet) {
			FrameworkLauncher.launchAgent(new Seller(elem));
		}

		FrameworkLauncher.launchAgent(new DamePipi(toiletSet));
		// Worker c = new Worker(WorkerTask.BODYGUARD);
		// FrameworkLauncher.launchAgent(c);
		//
		// Worker d = new Worker(WorkerTask.MED);
		// FrameworkLauncher.launchAgent(d);
		//

		FrameworkLauncher.startSimulation();
	}

	public static Set<Toilet> initToilets() {
		Set<Toilet> toiletSet = new HashSet<Toilet>();
		Set<StandAction> actions = new HashSet<StandAction>();
		actions.add(new StandAction(NeedType.PEE, -6));
		AttendantGender a = AttendantGender.MAN;

		Point2d pos = new Point2d(5, 90);
		Toilet toilet = new Toilet(15, pos, 15, "TOILET", a, actions);
		toiletSet.add(toilet);
		pos = new Point2d(500, 740);
		toilet = new Toilet(15, pos, 15, "TOILET", a, actions);
		toiletSet.add(toilet);
		pos = new Point2d(980, 690);
		toilet = new Toilet(15, pos, 15, "TOILET", a, actions);
		toiletSet.add(toilet);
		pos = new Point2d(900, 20);
		toilet = new Toilet(15, pos, 15, "TOILET", a, actions);
		toiletSet.add(toilet);
		pos = new Point2d(1420, 740);
		toilet = new Toilet(15, pos, 15, "TOILET", a, actions);
		toiletSet.add(toilet);
		pos = new Point2d(1700, 420);
		toilet = new Toilet(15, pos, 15, "TOILET", a, actions);
		toiletSet.add(toilet);

		a = AttendantGender.WOMAN;
		pos = new Point2d(5, 150);
		toilet = new Toilet(15, pos, 15, "TOILET", a, actions);
		toiletSet.add(toilet);
		pos = new Point2d(580, 740);
		toilet = new Toilet(15, pos, 15, "TOILET", a, actions);
		toiletSet.add(toilet);
		pos = new Point2d(1050, 740);
		toilet = new Toilet(15, pos, 15, "TOILET", a, actions);
		toiletSet.add(toilet);
		pos = new Point2d(980, 20);
		toilet = new Toilet(15, pos, 15, "TOILET", a, actions);
		toiletSet.add(toilet);
		pos = new Point2d(1500, 740);
		toilet = new Toilet(15, pos, 15, "TOILET", a, actions);
		toiletSet.add(toilet);
		pos = new Point2d(1700, 490);
		toilet = new Toilet(15, pos, 15, "TOILET", a, actions);
		toiletSet.add(toilet);

		return toiletSet;
	}

	public static Set<Stand> initStands() {
		Set<Stand> standList = new HashSet<Stand>();
		StandAction hungerAction = new StandAction(NeedType.HUNGER, -7);
		StandAction thirstAction = new StandAction(NeedType.THIRST, -5);
		StandAction peeAction = new StandAction(NeedType.PEE, 3);
		Set<StandAction> actions = new HashSet<StandAction>();
		actions.add(peeAction);
		actions.add(hungerAction);
		actions.add(thirstAction);

		Point2d pos = new Point2d(180, 20);
		Stand stand = new Stand(15, pos, 15, "FOODSTAND", actions);

		standList.add(stand);

		pos = new Point2d(260, 20);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(330, 20);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(770, 640);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(815, 690);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(870, 730);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);
		pos = new Point2d(750, 480);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(750, 400);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(850, 400);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(950, 400);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(850, 480);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(950, 480);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(1550, 280);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(1550, 350);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(1450, 280);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(1450, 350);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(1350, 280);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(1350, 350);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(1120, 20);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		pos = new Point2d(1220, 20);
		stand = new Stand(15, pos, 15, "FOODSTAND", actions);
		standList.add(stand);

		return standList;
	}
}