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
import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.gi.vi51.g3.framework.FrameworkLauncher;
import fr.utbm.gi.vi51.g3.framework.environment.EnvironmentEvent;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;
import fr.utbm.gi.vi51.g3.framework.gui.FrameworkGUI;
import fr.utbm.gi.vi51.g3.framework.tree.QuadTree;
import fr.utbm.gi.vi51.g3.framework.tree.QuadTreeNode;
import fr.utbm.gi.vi51.g3.motion.MainProgram;
import fr.utbm.gi.vi51.g3.motion.environment.WorldModelEvent;
import fr.utbm.gi.vi51.g3.motion.environment.WorldModelState;
import fr.utbm.gi.vi51.g3.motion.environment.WorldModelStateProvider;

public class GUI extends JFrame implements FrameworkGUI {

	private static final long serialVersionUID = -7098278003853923071L;

	private static final boolean SHOW_ICON = true;

	private static final String IMG_DIR = "images/";

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

	private static final int ICON_PEOPLE_WIDTH;
	private static final int ICON_PEOPLE_HEIGHT;
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
		setTitle(Locale.getString(MainProgram.class, "PROGRAM_NAME")); //$NON-NLS-1$

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
				//
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
	protected void setMouseTarget(Point2d p) {
		// synchronized (getTreeLock()) {
		// target = p;
		// if (environment != null) {
		// environment.setMouseTarget(target);
		// }
		// }
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
			if (mousePosition != null) {
				BOMB_ICON.paintIcon(this, g2d, (int) mousePosition.x
						- ICON_BOMB_SIZE / 2, (int) mousePosition.y
						- ICON_BOMB_SIZE / 2);
			}
			drawAgents(g2d, currentDim);
		}

		private void drawAgents(Graphics2D g2d, Dimension currentDim) {
			WorldModelState state = getLastState();
			if (state != null) {
				QuadTree tree = state.getWorldObjects();
				Iterator<QuadTreeNode> it = tree.iterator();
				while (it.hasNext()) {
					QuadTreeNode node = it.next();
					if ((node != null) && (node.getObject() != null)) {
						SituatedObject obj = node.getObject();
						drawObject(g2d, (int) obj.getX(), (int) obj.getY(),
								state.getObjectType(obj));
					}
				}
			}
		}
	}

		private void drawObject(Graphics2D g2d, int x, int y, String objectType) {
			if (SHOW_ICON && (objectType != null)) {
				switch (objectType) {
					
					case "TREE":
				TREE_ICON.paintIcon(this, g2d, x - (ICON_PEOPLE_WIDTH / 2), y
						- (ICON_PEOPLE_HEIGHT / 2));
						break;
			case "BARRIER":
				BARRIER_ICON.paintIcon(this, g2d, x - (ICON_PEOPLE_WIDTH / 2),
						y - (ICON_PEOPLE_HEIGHT / 2));
				break;
			case "BOMB":
				BOMB_ICON.paintIcon(this, g2d, x - (ICON_PEOPLE_WIDTH / 2), y
						- (ICON_PEOPLE_HEIGHT / 2));
				break;
					case "Beach":
				BEACHSTAGE_ICON
						.paintIcon(this, g2d, x - (ICON_PEOPLE_WIDTH / 2), y
								- (ICON_PEOPLE_HEIGHT / 2));
						break;
						
					case "FOODSTAND":
						FOODSTAND_ICON.paintIcon(this, g2d, x
 - (ICON_PEOPLE_WIDTH / 2), y
								- (ICON_PEOPLE_HEIGHT / 2));
						break;
					case "DRINKSTAND":
						DRINKSTAND_ICON.paintIcon(this, g2d, x
 - (ICON_PEOPLE_WIDTH / 2), y
								- (ICON_PEOPLE_HEIGHT / 2));
						break;
			case "TOILET_MAN":
				TOILET_MAN_ICON
						.paintIcon(this, g2d, x - (ICON_PEOPLE_WIDTH / 2), y
								- (ICON_PEOPLE_HEIGHT / 2));
						break;
			case "TOILET_WOMAN":
				TOILET_WOMAN_ICON
						.paintIcon(this, g2d, x - (ICON_PEOPLE_WIDTH / 2), y
								- (ICON_PEOPLE_HEIGHT / 2));
				break;
					case "MAN":
				MAN_ICON.paintIcon(this, g2d, x - (ICON_PEOPLE_WIDTH / 2), y
						- (ICON_PEOPLE_HEIGHT / 2));
						break;
					case "WOMAN":
				WOMAN_ICON.paintIcon(this, g2d, x - (ICON_PEOPLE_WIDTH / 2), y
						- (ICON_PEOPLE_HEIGHT / 2));
						break;
					case "MED":
				MED_ICON.paintIcon(this, g2d, x - (ICON_PEOPLE_WIDTH / 2), y
						- (ICON_PEOPLE_HEIGHT / 2));
						break;
					case "BODYGUARD":
						BODYGUARD_ICON.paintIcon(this, g2d, x
 - (ICON_PEOPLE_WIDTH / 2), y
								- (ICON_PEOPLE_HEIGHT / 2));
						break;
					case "SELLER":
				SELLER_ICON.paintIcon(this, g2d, x - (ICON_PEOPLE_WIDTH / 2), y
						- (ICON_PEOPLE_HEIGHT / 2));
						break;
					default:
						System.out.println("GUI.drawObject - pas de type trouvé");
					break;
				}

			}
		}

}
