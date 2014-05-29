/**
 *
 */
package fr.utbm.gi.vi51.g3.framework.tree;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Stack;
import java.util.function.Consumer;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;

/**
 * @author zarov
 *
 */
public class QuadTree implements Tree<QuadTreeNode> {

	@Override
	public void setRoot(TreeNode<QuadTreeNode> root) {
		// TODO Auto-generated method stub

	}

	@Override
	public QuadTreeNode getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Perception> cull(AABB box) {
		// TODO Auto-generated method stub
		return null;
	}

	class NodeIterator implements Iterator<QuadTreeNode> {

		private final Stack<QuadTreeNode> s = new Stack<>();

		public NodeIterator() {
			s.push(QuadTree.this.getRoot());
		}

		@Override
		public boolean hasNext() {
			return !s.isEmpty();
		}

		@Override
		public QuadTreeNode next() {
			QuadTreeNode n = s.pop();

			for (QuadTreeNode c : n.getChildren()) {
				s.push(c);
			}

			return n;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

		@Override
		public void forEachRemaining(Consumer<? super QuadTreeNode> action) {
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
			nodeIterator = ni;
			searchNext();
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public Perception next() {
			Perception n = next;
			searchNext();
			return n;
		}

		private void searchNext() {
			next = null;
			while (next == null) {
				if ((objectIterator == null) || !objectIterator.hasNext()) {
					while (objectIterator.hasNext()) {
						QuadTreeNode n = nodeIterator.next();
						if (n.getBox().intersects(frustrum)) {
							// TODO
							// objectIterator = n.getData().iterator();
						}
					}
				} else {
					return;
				}

				assert (objectIterator != null);
				if (objectIterator.hasNext()) {
					SituatedObject o = objectIterator.next();
					if (o.getBox().intersects(frustrum)) {
						next = o.toPerception();
					}
				}
			}
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

		@Override
		public void forEachRemaining(Consumer<? super Perception> action) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public Iterator<TreeNode<QuadTreeNode>> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void forEach(Consumer<? super TreeNode<QuadTreeNode>> action) {
		// TODO Auto-generated method stub

	}

	@Override
	public Spliterator<TreeNode<QuadTreeNode>> spliterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
