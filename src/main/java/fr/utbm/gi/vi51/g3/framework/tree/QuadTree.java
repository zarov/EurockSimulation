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

/**
 * @author zarov
 *
 */
public class QuadTree implements Tree<QuadTreeNode> {

	private QuadTreeNode root;

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Perception> cull(AABB box) {
		FrustumCuller fc = new FrustumCuller(iterator(), box);
		List<Perception> percepts = new ArrayList<>();

		while (fc.hasNext()) {
			percepts.add(fc.next());
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
		private final Iterator<TreeNode<QuadTreeNode>> nodeIterator;
		private Perception next;

		public FrustumCuller(Iterator<TreeNode<QuadTreeNode>> ni, AABB frustrum) {
			this.frustrum = frustrum;
			nodeIterator = ni;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return next != null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Perception next() {
			Perception n = next;
			// Compute the next object (perception)
			searchNext();
			return n;
		}

		/**
		 * Search the next available element from this iterator.
		 */
		private void searchNext() {
			next = null;

			if (nodeIterator != null) {
				while ((next == null) && nodeIterator.hasNext()) {
					TreeNode<QuadTreeNode> node = nodeIterator.next();
					// If the box of the object contained in the node intersects
					// with the frustum, then it is in the perception field
					if (node.getObject().getBox().intersects(frustrum)) {
						next = node.getObject().toPerception();
					}
				}
			}
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
	public Iterator<TreeNode<QuadTreeNode>> iterator() {
		return new NodeIterator();
	}

	/**
	 * The Iterator of the tree.
	 *
	 * @author zarov
	 *
	 */
	class NodeIterator implements Iterator<TreeNode<QuadTreeNode>> {

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

			for (QuadTreeNode c : n.getChildren()) {
				s.push(c);
			}

			return n;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void remove() {
			s.removeElement(n);
		}
	}

}