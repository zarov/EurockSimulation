package fr.utbm.gi.vi51.g3.motion.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.vecmath.Point2d;

import org.arakhne.afc.vmutil.Resources;
import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.gi.vi51.g3.framework.FrameworkLauncher;
import fr.utbm.gi.vi51.g3.framework.environment.Animat;
import fr.utbm.gi.vi51.g3.framework.environment.EnvironmentEvent;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;
import fr.utbm.gi.vi51.g3.framework.gui.FrameworkGUI;
import fr.utbm.gi.vi51.g3.motion.MainProgram;
import fr.utbm.gi.vi51.g3.motion.environment.WorldModelEvent;
import fr.utbm.gi.vi51.g3.motion.environment.WorldModelState;
import fr.utbm.gi.vi51.g3.motion.environment.WorldModelStateProvider;

public class GUI extends JFrame implements FrameworkGUI {

	/**
	 *
	 */
	private static final long serialVersionUID = -7098278003853923071L;

	private static final double DIRECTION_RADIUS = 60.;

	private static final boolean SHOW_ICON = true;

	private static final Icon MAN_ICON;
	private static final Icon WOMAN_ICON;
	private static final Icon SELLER_ICON;
	private static final Icon MED_ICON;
	private static final Icon BODYGUARD_ICON;
	private static final Icon BARRIER_ICON;
	private static final Icon TREE_ICON;
	private static final Icon STAGE_ICON;
	private static final Icon FOODSTAND_ICON;
	private static final Icon DRINKSTAND_ICON;
	private static final Icon TOILET_ICON;

	private static final int ICON_WIDTH;
	private static final int ICON_HEIGHT;

	static {
		URL url = Resources.getResource(GUI.class, "small_man.png"); //$NON-NLS-1$
		assert (url != null);
		MAN_ICON = new ImageIcon(url);
		url = Resources.getResource(GUI.class, "small_woman.png"); //$NON-NLS-1$
		assert (url != null);
		WOMAN_ICON = new ImageIcon(url);
		url = Resources.getResource(GUI.class, "small_seller.png"); //$NON-NLS-1$
		assert (url != null);
		SELLER_ICON = new ImageIcon(url);
		url = Resources.getResource(GUI.class, "small_med.png"); //$NON-NLS-1$
		assert (url != null);
		MED_ICON = new ImageIcon(url);
		url = Resources.getResource(GUI.class, "small_bodyguard.png"); //$NON-NLS-1$
		assert (url != null);
		BODYGUARD_ICON = new ImageIcon(url);
		url = Resources.getResource(GUI.class, "small_barrier.png"); //$NON-NLS-1$
		assert (url != null);
		BARRIER_ICON = new ImageIcon(url);
		url = Resources.getResource(GUI.class, "small_tree.png"); //$NON-NLS-1$
		assert (url != null);
		TREE_ICON = new ImageIcon(url);
		url = Resources.getResource(GUI.class, "small_tree.png"); //$NON-NLS-1$
		assert (url != null);
		TOILET_ICON = new ImageIcon(url);
		url = Resources.getResource(GUI.class, "small_tree.png"); //$NON-NLS-1$
		assert (url != null);
		FOODSTAND_ICON = new ImageIcon(url);
		url = Resources.getResource(GUI.class, "small_tree.png"); //$NON-NLS-1$
		assert (url != null);
		DRINKSTAND_ICON = new ImageIcon(url);
		url = Resources.getResource(GUI.class, "small_tree.png"); //$NON-NLS-1$
		assert (url != null);
		STAGE_ICON = new ImageIcon(url);

		ICON_WIDTH = MAN_ICON.getIconWidth();
		ICON_HEIGHT = MAN_ICON.getIconHeight();
	}

	private final World world;

	private Point2d target = null;

	private WorldModelState lastState = null;
	private WorldModelStateProvider environment = null;

	/**
	 * @param worldWidth
	 * @param worldHeight
	 */
	public GUI(double worldWidth, double worldHeight) {
		setTitle(Locale.getString(MainProgram.class, "PROGRAM_NAME")); //$NON-NLS-1$

		Container content = getContentPane();

		content.setLayout(new BorderLayout());

		this.world = new World();

		JScrollPane scroll = new JScrollPane(this.world);
		content.add(BorderLayout.CENTER, scroll);

		JButton closeBt = new JButton("Quit"); //$NON-NLS-1$
		closeBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameworkLauncher.stopSimulation();
			}
		});
		content.add(BorderLayout.SOUTH, closeBt);

		this.world.setPreferredSize(new Dimension((int) worldWidth,
				(int) worldHeight));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				FrameworkLauncher.stopSimulation();
			}
		});

		this.world.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// setMouseTarget(new Point2d(e.getX(), e.getY()));
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// setMouseTarget(new Point2d(e.getX(), e.getY()));
			}
		});

		this.world.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// setMouseTarget(new Point2d(e.getX(), e.getY()));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// setMouseTarget(null);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				//
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				//
			}
		});

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		pack();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void environmentChanged(EnvironmentEvent event) {
		synchronized (getTreeLock()) {
			if ((event instanceof WorldModelEvent)
					&& (this.environment == null)) {
				WorldModelEvent e = (WorldModelEvent) event;
				this.environment = e.getStateProvider();
			}
			this.lastState = this.environment.getState();
			repaint();
		}
	}

	/**
	 * @param p
	 */
	protected void setMouseTarget(Point2d p) {
		synchronized (getTreeLock()) {
			this.target = p;
			if (this.environment != null) {
				this.environment.setMouseTarget(this.target);
			}
		}
	}

	/**
	 * Replies the last environment state.
	 *
	 * @return the last environment state.
	 */
	protected WorldModelState getLastState() {
		return this.lastState;
	}

	/**
	 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
	 * @version $Name$ $Revision$ $Date$
	 */
	private class World extends JPanel {

		private static final long serialVersionUID = 8516008479029079959L;

		public World() {
			//
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2d = (Graphics2D) g;

			Dimension currentDim = getPreferredSize();

			drawAgents(g2d, currentDim);
		}

		private void drawAgents(Graphics2D g2d, Dimension currentDim) {
			WorldModelState state = getLastState();
			if (state != null) {
				for (SituatedObject p : state.getObjects()) {
					if (p instanceof Animat<?>) {
						drawAgent(
								g2d,
								(int) p.getX(),
								(int) p.getY(),
								(int) (p.getDirection().getX() * DIRECTION_RADIUS),
								(int) (p.getDirection().getY() * DIRECTION_RADIUS),
								state.getAgentType(p));
					} else {
						drawObject(g2d, (int) p.getX(), (int) p.getY(),
								state.getObjectType(p));
					}
				}
			}
		}

		private void drawObject(Graphics2D g2d, int x, int y, String objectType) {
			if (SHOW_ICON && (objectType != null)) {
				switch (objectType) {
					case "STAGE":
						STAGE_ICON.paintIcon(this, g2d, x - (ICON_WIDTH / 2), y
								- (ICON_HEIGHT / 2));
						break;
					case "FOODSTAND":
						FOODSTAND_ICON.paintIcon(this, g2d, x
								- (ICON_WIDTH / 2), y - (ICON_HEIGHT / 2));
						break;
					case "DRINKSTAND":
						DRINKSTAND_ICON.paintIcon(this, g2d, x
								- (ICON_WIDTH / 2), y - (ICON_HEIGHT / 2));
						break;
					case "TOILET":
						TOILET_ICON.paintIcon(this, g2d, x - (ICON_WIDTH / 2),
								y - (ICON_HEIGHT / 2));
						break;
					default:
						System.out
								.println("GUI.drawObject - pas de type trouv�");
						break;
				}

			}
		}

		@SuppressWarnings("synthetic-access")
		private void drawAgent(Graphics2D g2d, int x, int y, int dx, int dy,
				String agentType) {
			// g2d.setColor(Color.BLUE);
			// g2d.drawLine(x,y,x+dx,y+dy);
			if (SHOW_ICON && (agentType != null)) {
				switch (agentType) {
					case "MAN":
						MAN_ICON.paintIcon(this, g2d, x - (ICON_WIDTH / 2), y
								- (ICON_HEIGHT / 2));
						break;
					case "WOMAN":
						WOMAN_ICON.paintIcon(this, g2d, x - (ICON_WIDTH / 2), y
								- (ICON_HEIGHT / 2));
						break;
					case "MED":
						MED_ICON.paintIcon(this, g2d, x - (ICON_WIDTH / 2), y
								- (ICON_HEIGHT / 2));
						break;
					case "BODYGUARD":
						BODYGUARD_ICON.paintIcon(this, g2d, x
								- (ICON_WIDTH / 2), y - (ICON_HEIGHT / 2));
						break;
					case "SELLER":
						SELLER_ICON.paintIcon(this, g2d, x - (ICON_WIDTH / 2),
								y - (ICON_HEIGHT / 2));
						break;
					default:
						System.out
								.println("GUI.drawAgent - pas de type trouv�");
						break;
				}
			} else {
				g2d.drawOval(x - (ICON_WIDTH / 2), y - (ICON_HEIGHT / 2),
						ICON_WIDTH, ICON_HEIGHT);
			}
		}

	}

}
