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
package fr.utbm.gi.vi51.g3.framework;

import org.janusproject.kernel.Kernel;
import org.janusproject.kernel.KernelAdapter;
import org.janusproject.kernel.KernelEvent;
import org.janusproject.kernel.agent.Kernels;

import fr.utbm.gi.vi51.g3.framework.environment.Animat;
import fr.utbm.gi.vi51.g3.framework.environment.Environment;
import fr.utbm.gi.vi51.g3.framework.gui.FrameworkGUI;
import fr.utbm.gi.vi51.g3.framework.schedule.SimulationScheduler;

/**
 * Launcher of the simulation framework.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class FrameworkLauncher {
	
	private static Environment environmentSingleton = null;
	private static FrameworkGUI uiSingleton = null;

	/** Launch the environment and its associated UI.
	 * 
	 * @param environment is the environment to use.
	 * @param gui is the GUI to launch.
	 * @param waitDuration is the wait duration atthe end of each simulation step (in milliseconds).
	 */
	public static void launchEnvironment(Environment environment, FrameworkGUI gui, long waitDuration) {
		assert(environment!=null);
		if (environmentSingleton==null) {
			environmentSingleton = environment;
			uiSingleton = gui;
			if (gui!=null) environment.addEnvironmentListener(gui);
			Kernel k = Kernels.create(new SimulationScheduler(environment, waitDuration));
			k.addKernelListener(new KernelAdapter(){
				/**
				 * {@inheritDoc}
				 */
				@SuppressWarnings("synthetic-access")
				@Override
				public void agentKilled(KernelEvent event) {
					if (environmentSingleton!=null) {
						environmentSingleton.killAgentBody(event.getAgent());
					}
				}
			});
		}
	}
	
	/** Launch the given animat.
	 * 
	 * @param animat
	 */
	public static void launchAgent(Animat<?> animat) {
		if (environmentSingleton==null) throw new IllegalStateException();		 
		
		Kernel k = Kernels.get();
		if (k==null) throw new IllegalStateException();
		
		environmentSingleton.spawnAgentBody(animat);
		
		k.submitLightAgent(animat);
	}
	
	/** Start the simulation right now.
	 */
	public static void startSimulation() {
		Kernel k = Kernels.get();
		if (k==null) throw new IllegalStateException();
		k.launchDifferedExecutionAgents();
		if (uiSingleton!=null) uiSingleton.setVisible(true);
	}

	/** Stop the simulation right now.
	 */
	public static void stopSimulation() {
		Kernels.killAll();
		System.exit(0);
	}

}