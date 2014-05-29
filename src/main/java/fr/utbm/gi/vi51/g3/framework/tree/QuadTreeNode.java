/**
 *
 */
package fr.utbm.gi.vi51.g3.framework.tree;

import java.util.List;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;

/**
 * @author zarov
 *
 */
public class QuadTreeNode implements TreeNode<QuadTreeNode> {

	@Override
	public TreeNode<QuadTreeNode> getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParent(TreeNode<QuadTreeNode> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<QuadTreeNode> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setChildren(List<TreeNode<QuadTreeNode>> children) {
		// TODO Auto-generated method stub

	}

	@Override
	public AABB getBox() {
		// TODO Auto-generated method stub
		return null;
	}
}
