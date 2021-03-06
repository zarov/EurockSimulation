package fr.utbm.gi.vi51.g3.motion.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.vecmath.Point2d;

import org.arakhne.afc.vmutil.Resources;

import fr.utbm.gi.vi51.g3.framework.FrameworkLauncher;
import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;
import fr.utbm.gi.vi51.g3.framework.environment.EnvironmentEvent;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;
import fr.utbm.gi.vi51.g3.framework.gui.FrameworkGUI;
import fr.utbm.gi.vi51.g3.framework.tree.QuadTree;
import fr.utbm.gi.vi51.g3.framework.tree.QuadTreeNode;
import fr.utbm.gi.vi51.g3.motion.environment.WorldModelEvent;
import fr.utbm.gi.vi51.g3.motion.environment.WorldModelState;
import fr.utbm.gi.vi51.g3.motion.environment.WorldModelStateProvider;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Bomb;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stage;

public class GUI extends JFrame implements FrameworkGUI {

	private static final long serialVersionUID = -7098278003853923071L;

	private static final boolean SHOW_ICON = true;

	private static final String IMG_DIR = "images/";

	private static final int BOMB_TIMER =1;

	private static final Icon MAN_ICON;
	private static final Icon WOMAN_ICON;
	private static final Icon SELLER_ICON;
	private static final Icon MED_ICON;
	private static final Icon BODYGUARD_ICON;
	private static final Icon BARRIER_ICON;
	private static final Icon TREE_ICON;
	private static final Icon FOODSTAND_ICON;
	private static final Icon DRINKSTAND_ICON;
	private static final Icon TOILET_MAN_ICON;
	private static final Icon TOILET_WOMAN_ICON;
	private static final Icon LOGGIASTAGE_ICON;
	private static final Icon BEACHSTAGE_ICON;
	private static final Icon MAINSTAGE_ICON;
	private static final Icon GREENSTAGE_ICON;
	private static final Icon BOMB_ICON;
	private static final Icon TICKING_BOMB_ICON;

	private static final int ICON_PEOPLE_WIDTH;
	private static final int ICON_PEOPLE_HEIGHT;
	private static final int ICON_STAND_SIZE;
	private static final int ICON_BOMB_SIZE;

	static {
		URL url = Resources.getResource(GUI.class, IMG_DIR + "small_man.png"); //$NON-NLS-1$
		assert (url != null);
		MAN_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR + "small_woman.png"); //$NON-NLS-1$
		assert (url != null);
		WOMAN_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR + "small_seller.png"); //$NON-NLS-1$
		assert (url != null);
		SELLER_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR + "small_med.png"); //$NON-NLS-1$
		assert (url != null);
		MED_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR + "small_bodyguard.png"); //$NON-NLS-1$
		assert (url != null);
		BODYGUARD_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR + "small_barrier.png"); //$NON-NLS-1$
		assert (url != null);
		BARRIER_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR + "small_mantoilet.png"); //$NON-NLS-1$
		assert (url != null);
		TOILET_MAN_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR
				+ "small_womantoilet.png"); //$NON-NLS-1$
		assert (url != null);
		TOILET_WOMAN_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR + "small_tree.png"); //$NON-NLS-1$
		assert (url != null);
		TREE_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR + "small_bomb.png"); //$NON-NLS-1$
		assert (url != null);
		BOMB_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR
				+ "small_ticking_bomb.png"); //$NON-NLS-1$
		assert (url != null);
		TICKING_BOMB_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR + "small_foodstand.png"); //$NON-NLS-1$
		assert (url != null);
		FOODSTAND_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR + "small_foodstand.png"); //$NON-NLS-1$
		assert (url != null);
		DRINKSTAND_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR
				+ "beach_stage_empty.jpg");
		assert (url != null);
		BEACHSTAGE_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR + "main_stage.jpg");
		assert (url != null);
		MAINSTAGE_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR + "greenRoom_stage.jpg");
		assert (url != null);
		GREENSTAGE_ICON = new ImageIcon(url);

		url = Resources.getResource(GUI.class, IMG_DIR + "Loggia_stage.jpg");
		assert (url != null);
		LOGGIASTAGE_ICON = new ImageIcon(url);

		ICON_PEOPLE_WIDTH = MAN_ICON.getIconWidth();
		ICON_PEOPLE_HEIGHT = MAN_ICON.getIconHeight();
		ICON_BOMB_SIZE = BOMB_ICON.getIconHeight();
		ICON_STAND_SIZE = FOODSTAND_ICON.getIconHeight();
	}

	private final World world;

	private Point2d mousePosition = null;

	private WorldModelState lastState = null;
	private WorldModelStateProvider environment = null;

	/**
	 * @param worldWidth
	 * @param worldHeight
	 */
	public GUI(double worldWidth, double worldHeight) {
		setTitle("VI51 Project - Eurock�ennes simulation"); //$NON-NLS-1$

		Container content = getContentPane();

		content.setLayout(new BorderLayout());

		world = new World();

		JScrollPane scroll = new JScrollPane(world);
		content.add(BorderLayout.CENTER, scroll);

		JButton closeBt = new JButton("Quit"); //$NON-NLS-1$
		closeBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameworkLauncher.stopSimulation();
			}
		});
		content.add(BorderLayout.SOUTH, closeBt);

		world.setPreferredSize(new Dimension((int) worldWidth,
				(int) worldHeight));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				FrameworkLauncher.stopSimulation();
			}
		});

		world.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				mousePosition = new Point2d(e.getX(), e.getY());
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mousePosition = new Point2d(e.getX(), e.getY());
			}
		});

		world.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setBomb(new Point2d(e.getX() - (ICON_BOMB_SIZE / 2), e.getY()
						- (ICON_BOMB_SIZE / 2)));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				mousePosition = (new Point2d(e.getX(), e.getY()));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mousePosition = null;
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
			if ((event instanceof WorldModelEvent) && (environment == null)) {
				WorldModelEvent e = (WorldModelEvent) event;
				environment = e.getStateProvider();
			}
			lastState = environment.getState();
			repaint();
		}
	}

	/**
	 * @param p
	 */
	protected void setBomb(Point2d position) {
		environment.setBomb(new Bomb(2, position, 0, "BOMB", BOMB_TIMER,
				environment.getCurrentTime()));
		// synchronized (getTreeLock()) {
		// target = p;
		// if (environment != null) {
		// environment.setMouseTarget(target);
		// }
		// }
	}

	private boolean isEvenTime(WorldModelState state) {
		return ((state.getTime() % 1000) == 0);
	}

	/**
	 * Replies the last environment state.
	 *
	 * @return the last environment state.
	 */
	protected WorldModelState getLastState() {
		return lastState;
	}

	/**
	 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
	 * @version $Name$ $Revision$ $Date$
	 */
	private class World extends JPanel {

		private static final long serialVersionUID = 8516008479029079959L;

		public World() {

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2d = (Graphics2D) g;

			Dimension currentDim = getPreferredSize();

			WorldModelState state = getLastState();
			drawObjects(g2d, currentDim, state);
			drawAgents(g2d, currentDim, state);

			if (environment.getBomb() == null) {
				if (mousePosition != null) {
					BOMB_ICON.paintIcon(this, g2d, (int) mousePosition.x
							- (ICON_BOMB_SIZE / 2), (int) mousePosition.y
							- (ICON_BOMB_SIZE / 2));
				}
			} else {
				TICKING_BOMB_ICON.paintIcon(this, g2d, (int) environment
						.getBomb().getX(), (int) environment.getBomb().getY());
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				Font font = new Font("Serif", Font.PLAIN, 8);
				g2d.setFont(font);

				g2d.drawString(Integer.toString((int) environment.getBomb()
						.getTimeBeforeExplosion()), (int) environment.getBomb()
						.getX(), (int) environment.getBomb().getY());
				
//				g2d.setColor(new Color(150, 150, 150,70));
//				AABB hop = environment.getBomb().getFrustum();
//				int erfx = (int) environment.getBomb().getPosition().x;
//				double x = hop.getXlow();
//				double y = hop.getYlow();
//				
//				double w = hop.getXup()-hop.getXlow();
//				double h = hop.getYup()-hop.getYlow();
//				g2d.fillRect((int) x, (int)y, (int)w,(int) h);
				
				g2d.setColor(new Color(255, 0, 0,50));
				AABB Frust = environment.getBomb().getFrustum();
				int x1 = (int) environment.getBomb().getPosition().x + (ICON_BOMB_SIZE / 2);
				int y1= (int) environment.getBomb().getPosition().y + (ICON_BOMB_SIZE / 2);
				g2d.fillOval(x1-50, y1-50, 100, 100);
				
				g2d.setColor(new Color(150, 150, 150,50));
				g2d.fillOval(x1-100, y1-100, 200, 200);
				
				 
			}
		}

		private void drawObjects(Graphics2D g2d, Dimension currentDim,
				WorldModelState state) {
			if (state != null) {
				QuadTree tree = state.getWorldObjects();
				Iterator<QuadTreeNode> it = tree.iterator();
				while (it.hasNext()) {
					QuadTreeNode node = it.next();
					if ((node != null) && (node.getObject() != null)
							&& !(node.getObject() instanceof AgentBody)) {
						SituatedObject obj = node.getObject();
						if (obj instanceof Stage) {
							drawObject(g2d, (int) obj.getX(), (int) obj.getY(),
									state.getObjectType(obj),
									((Stage) obj).getWidth(),
									((Stage) obj).getHeight(),
									((Stage) obj).isOnAir(), isEvenTime(state));
						} else {
							drawObject(g2d, (int) obj.getX(), (int) obj.getY(),
									state.getObjectType(obj), 0, 0, false,
									false);
						}
					}
				}
			}
		}

		private void drawAgents(Graphics2D g2d, Dimension currentDim,
				WorldModelState state) {
			if (state != null) {
				QuadTree tree = state.getWorldObjects();
				Iterator<QuadTreeNode> it = tree.iterator();
				while (it.hasNext()) {
					QuadTreeNode node = it.next();
					if ((node != null) && (node.getObject() != null)
							&& (node.getObject() instanceof AgentBody)) {
						SituatedObject obj = node.getObject();
						drawAgent(g2d, (int) obj.getX(), (int) obj.getY(),
								state.getObjectType(obj));
					}
				}
			}
		}
	}

	private void drawObject(Graphics2D g2d, int x, int y, String objectType,
			int width, int height, boolean onAir, boolean isEvenTime) {
		if (SHOW_ICON && (objectType != null)) {
			// int scaleStage = 0;
			// if (isEvenTime) {
			// scaleStage = 10;
			// }
			// if (onAir)
			// g2d.setColor(Color.cyan);
			// else
			// g2d.setColor(Color.gray);
			switch (objectType) {
				case "TREE":
				TREE_ICON.paintIcon(this, g2d, x - (ICON_STAND_SIZE / 2), y
						- (ICON_STAND_SIZE / 2));
					break;
				case "BARRIER":
					BARRIER_ICON.paintIcon(this, g2d, x
 - (ICON_STAND_SIZE / 2), y
						- (ICON_STAND_SIZE / 2));
					break;

				case "GATE":

					g2d.fillRect(x - (100 / 2), y - (100 / 2), 50, 200);

					break;

				case "Beach":
					// BEACHSTAGE_ICON.paintIcon(this, g2d, x -
					// (ICON_PEOPLE_WIDTH /
					// 2), y
					// - (ICON_PEOPLE_HEIGHT / 2));
					g2d.drawRect(x - (width / 2), y - (height / 2), width,
							height);
					break;

				case "Main":
					// MAINSTAGE_ICON.paintIcon(this, g2d, x -
					// (ICON_PEOPLE_WIDTH /
					// 2), y
					// - (ICON_PEOPLE_HEIGHT / 2));
					g2d.drawRect(x - (width / 2), y - (height / 2), width,
							height);
					break;

				case "Greenroom":
					// GREENSTAGE_ICON.paintIcon(this, g2d, x -
					// (ICON_PEOPLE_WIDTH /
					// 2), y
					// - (ICON_PEOPLE_HEIGHT / 2));
					g2d.drawRect(x - (width / 2), y - (height / 2), width,
							height);
					break;

				case "Loggia":
					// LOGGIASTAGE_ICON.paintIcon(this, g2d, x -
					// (ICON_PEOPLE_WIDTH
					// / 2), y
					// - (ICON_PEOPLE_HEIGHT / 2));
					g2d.drawRect(x - (width / 2), y - (height / 2), width,
							height);
					break;

				case "FOODSTAND":
					FOODSTAND_ICON.paintIcon(this, g2d, x
 - (ICON_STAND_SIZE / 2),
						y - (ICON_STAND_SIZE / 2));
					break;
				case "DRINKSTAND":
					DRINKSTAND_ICON.paintIcon(this, g2d, x
 - (ICON_STAND_SIZE / 2),
						y - (ICON_STAND_SIZE / 2));
					break;
				case "TOILET_MAN":
					TOILET_MAN_ICON.paintIcon(this, g2d, x
 - (ICON_STAND_SIZE / 2),
						y - (ICON_STAND_SIZE / 2));
					break;
				case "TOILET_WOMAN":
					TOILET_WOMAN_ICON.paintIcon(this, g2d, x
						- (ICON_STAND_SIZE / 2), y - (ICON_STAND_SIZE / 2));
					break;
				default:
					break;
			}
		}

	}

	private void drawAgent(Graphics2D g2d, int x, int y, String objectType) {
		if (SHOW_ICON && (objectType != null)) {
			switch (objectType) {
				case "MAN":
					MAN_ICON.paintIcon(this, g2d, x - (ICON_PEOPLE_WIDTH / 2),
							y - (ICON_PEOPLE_HEIGHT / 2));
					break;
				case "WOMAN":
					WOMAN_ICON.paintIcon(this, g2d,
							x - (ICON_PEOPLE_WIDTH / 2), y
									- (ICON_PEOPLE_HEIGHT / 2));
					break;
				case "MED":
					MED_ICON.paintIcon(this, g2d, x - (ICON_PEOPLE_WIDTH / 2),
							y - (ICON_PEOPLE_HEIGHT / 2));
					break;
				case "BODYGUARD":
					BODYGUARD_ICON.paintIcon(this, g2d, x
							- (ICON_PEOPLE_WIDTH / 2), y
							- (ICON_PEOPLE_HEIGHT / 2));
					break;
				case "SELLER":
					SELLER_ICON.paintIcon(this, g2d, x
							- (ICON_PEOPLE_WIDTH / 2), y
							- (ICON_PEOPLE_HEIGHT / 2));
					break;
				default:
					break;
			}
		}
	}
}
