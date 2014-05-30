/**
 *
 */
package fr.utbm.gi.vi51.g3.framework.tree;

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
public class QuadTree implements Tree<QuadTreeNode> {

	private QuadTreeNode root;

	@Override
	public void setRoot(QuadTreeNode root) {
		this.root = root;
	}

	@Override
	public QuadTreeNode getRoot() {
		return this.root;
	}

	@Override
	public List<Perception> cull(AABB box) {
		// TODO Auto-generated method stub
		return null;
	}

	class NodeIterator implements Iterator<QuadTreeNode> {

		private final Stack<QuadTreeNode> s = new Stack<>();

		public NodeIterator() {
			this.s.push(QuadTree.this.getRoot());
		}

		@Override
		public boolean hasNext() {
			return !this.s.isEmpty();
		}

		@Override
		public QuadTreeNode next() {
			QuadTreeNode n = this.s.pop();

			for (QuadTreeNode c : n.getChildren()) {
				this.s.push(c);
			}

			return n;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}
	}

	class FrustrumCuller implements Iterator<Perception> {
		private final AABB frustrum;
		private final Iterator<QuadTreeNode> nodeIterator;
		private Iterator<SituatedObject> objectIterator;
		private Perception next;

		public FrustrumCuller(Iterator<QuadTreeNode> ni, AABB frustrum) {
			this.frustrum = frustrum;
			this.nodeIterator = ni;
			searchNext();
		}

		@Override
		public boolean hasNext() {
			return this.next != null;
		}

		@Override
		public Perception next() {
			Perception n = this.next;
			searchNext();
			return n;
		}

		private void searchNext() {
			this.next = null;
			while (this.next == null) {
				if ((this.objectIterator == null) || !this.objectIterator.hasNext()) {
					while (this.objectIterator.hasNext()) {
						QuadTreeNode n = this.nodeIterator.next();
						if (n.getBox().intersects(this.frustrum)) {
							// TODO this.objectIterator = n.getData().iterator();
						}
					}
				} else {
					return;
				}

				assert (this.objectIterator != null);
				if (this.objectIterator.hasNext()) {
					SituatedObject o = this.objectIterator.next();
					// TODO if (o.getPosition().intersects(this.frustrum)) {
					//	this.next = o.toPerception();
					//}
				}
			}
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public Iterator<TreeNode<QuadTreeNode>> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}