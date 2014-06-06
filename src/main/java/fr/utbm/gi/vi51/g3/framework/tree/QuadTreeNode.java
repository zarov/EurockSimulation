/**
 *
 */
package fr.utbm.gi.vi51.g3.framework.tree;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;

/**
 * A node of a QuadTree.
 *
 * @author zarov
 *
 */
public class QuadTreeNode implements TreeNode<QuadTreeNode> {

	private final AABB box;
	private SituatedObject object;

	private QuadTreeNode parent;

	private QuadTreeNode northWestChild;
	private QuadTreeNode northEastChild;
	private QuadTreeNode southWestChild;
	private QuadTreeNode southEastChild;

	public QuadTreeNode(AABB box) {
		this.box = box;
		setObject(null);
		parent = null;
	}

	public QuadTreeNode(AABB box, QuadTreeNode parent) {
		this.box = box;
		setObject(null);
		this.parent = parent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TreeNode<QuadTreeNode> getParent() {
		return parent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setParent(QuadTreeNode parent) {
		this.parent = parent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<QuadTreeNode> getChildren() {
		List<QuadTreeNode> children = new ArrayList<>();
		children.add(northWestChild);
		children.add(northEastChild);
		children.add(southWestChild);
		children.add(southEastChild);
		return children;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setChildren(List<QuadTreeNode> children) {
		northWestChild = children.get(0);
		northEastChild = children.get(1);
		southWestChild = children.get(2);
		southEastChild = children.get(3);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AABB getBox() {
		return box;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SituatedObject getObject() {
		return object;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(SituatedObject object) {
		this.object = object;
	}

	public boolean insert(SituatedObject object) {
		if (box.contains(object)) {
			if ((this.object == null) && (northWestChild == null)) {
				setObject(object);
				return true;
			} else {
				if (northWestChild == null) {
					subdivide();
				}
				if (northWestChild.insert(object)) {
					return true;
				}
				if (northEastChild.insert(object)) {
					return true;
				}
				if (southWestChild.insert(object)) {
					return true;
				}
				if (southEastChild.insert(object)) {
					return true;
				}
			}
		}

		return false;
	}

	private void subdivide() {
		northWestChild = new QuadTreeNode(new AABB(box.getXlow(),
				box.getXmid(), box.getYmid(), box.getYup()));
		northEastChild = new QuadTreeNode(new AABB(box.getXmid(), box.getXup(),
				box.getYmid(), box.getYup()));
		southWestChild = new QuadTreeNode(new AABB(box.getXlow(),
				box.getXmid(), box.getYlow(), box.getYmid()));
		southEastChild = new QuadTreeNode(new AABB(box.getXmid(), box.getXup(),
				box.getYlow(), box.getYmid()));
		insert(object);
		object = null;
	}

}
