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

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point2d;

import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.gi.vi51.g3.framework.FrameworkLauncher;
import fr.utbm.gi.vi51.g3.framework.environment.Environment;
import fr.utbm.gi.vi51.g3.framework.gui.FrameworkGUI;
import fr.utbm.gi.vi51.g3.motion.agent.Attendant;
import fr.utbm.gi.vi51.g3.motion.agent.AttendantGender;
import fr.utbm.gi.vi51.g3.motion.agent.Seller;
import fr.utbm.gi.vi51.g3.motion.behaviour.decisionBehaviour.NeedType;
import fr.utbm.gi.vi51.g3.motion.environment.WorldModel;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stand;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.StandAction;
import fr.utbm.gi.vi51.g3.motion.gui.GUI;


public class MainProgram {

	private static double WORLD_SIZE_X = 1780;
	private static double WORLD_SIZE_Y = 780;
	private static long EXECUTION_DELAY = 50;

	/** Main program.
	 * 
	 * @param argv are the command line arguments.
	 */
	public static void main(String[] argv) {

		System.out.println(Locale.getString(MainProgram.class, "INTRO_MESSAGE")); //$NON-NLS-1$

		System.out.println("--- GUI initialization");
		FrameworkGUI gui = new GUI(WORLD_SIZE_X, WORLD_SIZE_Y);

		System.out.println("--- Environment initialization");
		Environment environment = new WorldModel(WORLD_SIZE_X, WORLD_SIZE_Y);

		FrameworkLauncher.launchEnvironment(environment, gui, EXECUTION_DELAY);

		System.out.println("Creating Agents ...");
		
		for (int i = 0; i < 200; i++) {
			FrameworkLauncher.launchAgent(new Attendant(AttendantGender.MAN));
			FrameworkLauncher.launchAgent(new Attendant(AttendantGender.WOMAN));
		}

		List<Stand> standList = new ArrayList<Stand>();
		StandAction satisfyHunger = new StandAction(NeedType.HUNGER, -7);
		
		Point2d pos = new Point2d(180,20);
		Stand stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(260, 20);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(330, 20);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));

		pos = new Point2d(770, 640);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(815, 690);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(870, 730);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		
		pos = new Point2d(750, 480);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(750, 400);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(850, 400);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(950, 400);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(850, 480);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(950, 480);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));

		pos = new Point2d(1550, 280);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(1550, 350);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		
		pos = new Point2d(1450, 280);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(1450, 350);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(1350, 280);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(1350, 350);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(1120, 20);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));
		pos = new Point2d(1220, 20);
		stand = new Stand(15, pos, 15, "FOODSTAND", satisfyHunger);
		standList.add(stand);
		FrameworkLauncher.launchAgent(new Seller(stand));

		// Worker c = new Worker(WorkerTask.BODYGUARD);
		// FrameworkLauncher.launchAgent(c);
		//
		// Worker d = new Worker(WorkerTask.MED);
		// FrameworkLauncher.launchAgent(d);
		//
		
		FrameworkLauncher.startSimulation();
	}

}