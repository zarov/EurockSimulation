/**
 *
 */
package fr.utbm.gi.vi51.g3.framework.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;

/**
 * @author zarov
 *
 */
public class QuadTree implements Tree<QuadTreeNode> {

	private QuadTreeNode root = null;

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
		return this.root;
	}

	public boolean insert(SituatedObject obj) {
		return root.insert(obj);		
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
		private final Iterator<TreeNode<QuadTreeNode>> nodeIterator;

		public FrustumCuller(Iterator<TreeNode<QuadTreeNode>> ni, AABB frustrum) {
			this.frustrum = frustrum;
			this.nodeIterator = ni;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return this.nodeIterator.hasNext();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Perception next() {
			Perception next = null;

			if (this.nodeIterator != null) {
				while ((next == null) && this.nodeIterator.hasNext()) {
					TreeNode<QuadTreeNode> node = this.nodeIterator.next();
					if (node != null) {
						AABB nBox = node.getBox();
						// If the box of the node intersects with the frustum,
						// we can go beyond it
						if ((nBox != null) && nBox.intersects(this.frustrum)) {
							// If the box of the object contained in the node
							// intersects with the frustum, then it is in the
							// perception field
							SituatedObject object = node.getObject();
							if ((object != null)
									&& object.getBox().intersects(this.frustrum)) {
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
			this.s.push(getRoot());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return !this.s.isEmpty();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public QuadTreeNode next() {
			this.n = this.s.pop();
			if (this.n != null) {
				List<QuadTreeNode> nChildren = this.n.getChildren();
				if ((nChildren != null) && !nChildren.isEmpty()) {
					for (QuadTreeNode c : nChildren) {
						this.s.push(c);
					}
				}
			}
			return this.n;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void remove() {
			this.s.removeElement(this.n);
		}
	}


}