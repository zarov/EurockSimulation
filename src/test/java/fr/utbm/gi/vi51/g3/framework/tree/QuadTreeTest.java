package fr.utbm.gi.vi51.g3.framework.tree;

import java.util.List;

import javax.vecmath.Point2d;

import org.junit.Test;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Flora;

public class QuadTreeTest {

	QuadTree tree;
	QuadTreeNode root;

	@Test
	public void testBuild() {
		tree = new QuadTree(8, 8);

		// First level
		// root = new QuadTreeNode(new AABB(0, 8, 0, 8));
		// System.out.println("once");
		// // Second level
		// List<QuadTreeNode> children = new ArrayList<>();
		// children.add(new QuadTreeNode(new AABB(0, 4, 0, 4), root));
		// children.add(new QuadTreeNode(new AABB(4, 8, 0, 4), root));
		// children.add(new QuadTreeNode(new AABB(0, 4, 4, 8), root));
		// children.add(new QuadTreeNode(new AABB(4, 8, 4, 8), root));
		//
		// root.setChildren(children);
		//
		// // Third level
		// List<QuadTreeNode> gChildren = new ArrayList<>();
		// // NW
		// gChildren.add(new QuadTreeNode(new AABB(0, 2, 0, 2),
		// children.get(0)));
		// gChildren.get(0).insert(new Flora(1, new Point2d(0.5, 1)));
		// gChildren.add(new QuadTreeNode(new AABB(2, 4, 0, 2),
		// children.get(0)));
		// gChildren.get(1).insert(new Flora(1.5, new Point2d(3, 1)));
		// gChildren.add(new QuadTreeNode(new AABB(0, 2, 2, 4),
		// children.get(0)));
		// gChildren.add(new QuadTreeNode(new AABB(2, 4, 2, 4),
		// children.get(0)));
		// children.get(0).setChildren(gChildren);
		// gChildren.clear();
		//
		// // NE
		// gChildren.add(new QuadTreeNode(new AABB(4, 6, 4, 6),
		// children.get(1)));
		// gChildren.add(new QuadTreeNode(new AABB(6, 8, 4, 6),
		// children.get(1)));
		// gChildren.get(1).insert(new Flora(1, new Point2d(6.5, 5)));
		// gChildren.add(new QuadTreeNode(new AABB(4, 6, 6, 8),
		// children.get(1)));
		// gChildren.get(2).insert(new Flora(0.5, new Point2d(5, 7)));
		// gChildren.add(new QuadTreeNode(new AABB(6, 8, 6, 8),
		// children.get(1)));
		// children.get(1).setChildren(gChildren);
		// gChildren.clear();
		//
		// // SW
		// gChildren.add(new QuadTreeNode(new AABB(0, 2, 4, 6),
		// children.get(2)));
		// gChildren.add(new QuadTreeNode(new AABB(2, 4, 4, 6),
		// children.get(2)));
		// gChildren.add(new QuadTreeNode(new AABB(0, 2, 6, 8),
		// children.get(2)));
		// gChildren.add(new QuadTreeNode(new AABB(2, 4, 6, 8),
		// children.get(2)));
		// gChildren.get(3).insert(new Flora(2, new Point2d(3, 5)));
		// children.get(2).setChildren(gChildren);
		// gChildren.clear();
		
		// tree.setRoot(root);
		System.out.println("flora 1");
		tree.insert(new Flora(2, new Point2d(3, 5)));
		System.out.println("flora 2");
		tree.insert(new Flora(0.5, new Point2d(5, 7)));
		System.out.println("flora 3");
		tree.insert(new Flora(1, new Point2d(6.5, 5)));
		System.out.println("flora 4");
		tree.insert(new Flora(1, new Point2d(0.5, 1)));
		System.out.println("flora 5");
		tree.insert(new Flora(1.5, new Point2d(3, 1)));
		
	}

	@Test
	public void testCull() {
		testBuild();

		// Let's define a frustum
		AABB frustum = new AABB(0, 6, 0, 6);
		// When called, cull function should return a list of 3 objects
		// CM EDIT should be 4 perceived objects to me : flora 1, 3, 4, 5.
		List<Perception> percepts = tree.cull(frustum);

		System.out.println("Total number of perceived objects : "
				+ percepts.size());
		for (Perception p : percepts) {
			System.out.println(p.getPerceivedObject().getClass().toString()
					+ " - " + p.getPerceivedObject().getBox().toString());
		}
	}

}
