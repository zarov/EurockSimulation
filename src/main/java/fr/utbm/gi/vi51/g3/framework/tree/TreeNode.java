/**
 *
 */
package fr.utbm.gi.vi51.g3.framework.tree;

import java.util.List;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;

/**
 *
 * @author zarov
 *
 * @param <N>
 * @param <E>
 */
public interface TreeNode<N extends TreeNode<N>> {
	/**
	 * Get the parent of this node.
	 *
	 * @return the parent
	 */
	TreeNode<N> getParent();

	/**
	 * Set the parent of this node.
	 *
	 * @param parent
	 *            the parent of this node.
	 */
	void setParent(N parent);

	/**
	 * Get the list of children of this node.
	 *
	 * @return a list a children
	 */
	List<N> getChildren();

	/**
	 * Set the list of children for this node.
	 *
	 * @param children
	 *            the list of children
	 */
	void setChildren(List<N> children);

	/**
	 * Get the AABB box of the node.
	 *
	 * @return
	 */
	AABB getBox();

	/**
	 * Get the SituatedObject contained in the node.
	 * 
	 * @return
	 */
	SituatedObject getObject();

	/**
	 * Set the SituatedObject contained in the node.
	 * 
	 * @param object
	 */
	void setObject(SituatedObject object);
}
