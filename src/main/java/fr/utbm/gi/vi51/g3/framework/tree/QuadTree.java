/**
 *
 */
package fr.utbm.gi.vi51.g3.framework.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;

/**
 * @author zarov
 *
 */
public class QuadTree implements Tree<QuadTreeNode>, Cloneable {

	private QuadTreeNode root;

	public QuadTree(double width, double height) {
		root = new QuadTreeNode(new AABB(0, width, 0, height));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRoot(QuadTreeNode root) {
		this.root = root;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QuadTreeNode getRoot() {
		return root;
	}

	public boolean insert(SituatedObject obj) {
		return root.insert(obj);
	}

	/**
	 * Remove the object from the tree. DOES NOT REMOVE THE QUADTREENODE.
	 * 
	 * @param obj
	 *            the object to remove
	 * @return
	 */
	public boolean remove(SituatedObject obj) {
		QuadTreeNode node = find(obj);

		if ((node == null) || !node.getObject().equals(obj)) {
			return false;
		}

		node.setObject(null);

		return true;
	}

	/**
	 * Find the node containing the object.
	 * 
	 * @param obj
	 *            the object to find
	 * @return
	 */
	public QuadTreeNode find(SituatedObject obj) {
		NodeIterator it = new NodeIterator();
		QuadTreeNode node = null;

		while (it.hasNext()) {
			node = it.next();

			// Check if a node is containing the object
			// If he isn't, we delete his children from the stack
			if (!node.getBox().contains(obj)) {
				it.remove(4);
			}
		}

		return node;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Perception> cull(AABB frustum) {
		FrustumCuller fc = new FrustumCuller(iterator(), frustum);
		List<Perception> percepts = new ArrayList<>();

		while (fc.hasNext()) {
			Perception p = fc.next();
			if (p != null) {
				percepts.add(p);
			}
		}

		return percepts;
	}

	/**
	 * A Frustum Culler is used to remove from the perception list of an agent,
	 * the object that are completely outside the view frustums.
	 *
	 * @author zarov
	 *
	 */
	class FrustumCuller implements Iterator<Perception> {
		private final AABB frustrum;
		private final Iterator<QuadTreeNode> nodeIterator;

		public FrustumCuller(Iterator<QuadTreeNode> ni, AABB frustrum) {
			this.frustrum = frustrum;
			nodeIterator = ni;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return nodeIterator.hasNext();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Perception next() {
			Perception next = null;

			if (nodeIterator != null) {
				while ((next == null) && nodeIterator.hasNext()) {
					TreeNode<QuadTreeNode> node = nodeIterator.next();
					if (node != null) {
						AABB nBox = node.getBox();
						// If the box of the node intersects with the frustum,
						// we can go beyond it
						if ((nBox != null) && nBox.intersects(frustrum)) {
							// If the box of the object contained in the node
							// intersects with the frustum, then it is in the
							// perception field
							SituatedObject object = node.getObject();
							if ((object != null)
									&& object.getBox().intersects(frustrum)) {
								next = object.toPerception();
							}
						}
					}
				}
			}

			return next;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<QuadTreeNode> iterator() {
		return new NodeIterator();
	}

	/**
	 * The Iterator of the tree.
	 *
	 * @author zarov
	 *
	 */
	class NodeIterator implements Iterator<QuadTreeNode> {

		private final Stack<QuadTreeNode> s = new Stack<>();
		private QuadTreeNode n;

		public NodeIterator() {
			s.push(getRoot());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return !s.isEmpty();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public QuadTreeNode next() {
			n = s.pop();
			if (n != null) {
				List<QuadTreeNode> nChildren = n.getChildren();
				if ((nChildren != null) && !nChildren.isEmpty()) {
					for (QuadTreeNode c : nChildren) {
						s.push(c);
					}
				}
			} else {
				if (hasNext()) {
					n = next();
				}
			}

			return n;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void remove() {
			s.pop();
		}

		/**
		 * Removes n elements of the stack.
		 *
		 * @param n
		 */
		public void remove(int n) {
			do {
				s.pop();
			} while (n > 0);
		}
	}

	@Override
	public QuadTree clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}

		return (QuadTree) o;
	}
}