/**
 *
 */
package fr.utbm.gi.vi51.g3.framework.tree;

import java.util.List;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;

/**
 *
 * @author zarov
 *
 * @param <N>
 */
public interface Tree<N extends TreeNode<N>> extends Iterable<TreeNode<N>> {
	/**
	 * Set the root of the tree.
	 *
	 * @param root
	 *            the treenode which is the root
	 */
	void setRoot(TreeNode<N> root);

	/**
	 * Get the root of the tree.
	 *
	 * @return the treenode root
	 */
	TreeNode<N> getRoot();

	/**
	 * Get the perceived objects of a specified box.
	 *
	 * @param box
	 * @return the perceived objects
	 */
	List<Perception> cull(AABB box);
}
