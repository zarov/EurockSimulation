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

import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.gi.vi51.g3.framework.FrameworkLauncher;
import fr.utbm.gi.vi51.g3.framework.environment.Environment;
import fr.utbm.gi.vi51.g3.framework.gui.FrameworkGUI;
import fr.utbm.gi.vi51.g3.motion.agent.Attendant;
import fr.utbm.gi.vi51.g3.motion.environment.WorldModel;
import fr.utbm.gi.vi51.g3.motion.gui.GUI;


public class MainProgram {

	private static double WORLD_SIZE_X = 700;
	private static double WORLD_SIZE_Y = 700;
	private static long EXECUTION_DELAY = 50;

	/** Main program.
	 * 
	 * @param argv are the command line arguments.
	 */
	public static void main(String[] argv) {

		System.out.println(Locale.getString(MainProgram.class, "INTRO_MESSAGE")); //$NON-NLS-1$

		//		boolean steering = JOptionPane.showConfirmDialog(null,
		//				Locale.getString(MainProgram.class, "USE_STEERING_MESSAGE"), //$NON-NLS-1$
		//				Locale.getString(MainProgram.class, "USE_STEERING_TITLE"), //$NON-NLS-1$
		//				JOptionPane.YES_NO_OPTION,
		//				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;

		FrameworkGUI gui = new GUI(WORLD_SIZE_X, WORLD_SIZE_Y);

		Environment environment = new WorldModel(WORLD_SIZE_X, WORLD_SIZE_Y);

		FrameworkLauncher.launchEnvironment(environment, gui, EXECUTION_DELAY);


		Attendant a = new Attendant() ;
		FrameworkLauncher.launchAgent(a) ;

		FrameworkLauncher.startSimulation();
	}

}