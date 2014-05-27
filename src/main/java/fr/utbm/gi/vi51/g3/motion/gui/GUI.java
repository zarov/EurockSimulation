package fr.utbm.gi.vi51.g3.motion.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.utbm.gi.vi51.g3.framework.environment.EnvironmentEvent;
import fr.utbm.gi.vi51.g3.framework.gui.FrameworkGUI;

public class GUI extends JFrame implements FrameworkGUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7098278003853923071L;

	private World world;

	public GUI(double wORLD_SIZE_X, double wORLD_SIZE_Y) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void environmentChanged(EnvironmentEvent event) {
		// TODO Auto-generated method stub

	}

	private class World extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1220070899892091168L;

		public World(){}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2d = (Graphics2D)g;

			Dimension currentDim = getPreferredSize();

			drawAgents(g2d, currentDim);
			drawObjects(g2d, currentDim);
		}

		private void drawAgents(Graphics2D g2d, Dimension currentDim){

		}

		private void drawObjects(Graphics2D g2d, Dimension currentDim){

		}

		private void drawAgent(){}

		private void drawObject(){}

	}

}
