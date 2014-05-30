/**
 *
 */
package fr.utbm.gi.vi51.g3.framework.tree;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;

/**
 * @author zarov
 *
 */
public class QuadTreeNode implements TreeNode<QuadTreeNode> {

	private AABB box;
	private SituatedObject object;

	private QuadTreeNode northWestChild = null;
	private QuadTreeNode northEastChild = null;
	private QuadTreeNode southWestChild = null;
	private QuadTreeNode southEastChild = null;

	public QuadTreeNode(AABB box){
		this.box = box;
		this.object = null;
	}

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
		List<QuadTreeNode> children = new ArrayList<>();
		children.add(this.northWestChild);
		children.add(this.northEastChild);
		children.add(this.southWestChild);
		children.add(this.southEastChild);
		return children;
	}

	@Override
	public void setChildren(List<QuadTreeNode> children) {
		// TODO Auto-generated method stub
	}

	@Override
	public AABB getBox() {
		return this.box;
	}

	public boolean insert(SituatedObject object){
		if(this.box.contains(object)) {
			if(object == null){
				this.object = object;
				return true;
			} else {
				if(this.northWestChild == null){
					subdivide();
					if(this.northWestChild.insert(object)) return true;
					if(this.northEastChild.insert(object)) return true;
					if(this.southWestChild.insert(object)) return true;
					if(this.southEastChild.insert(object)) return true;
				}
			}
		}

		return false;
	}

	private void subdivide(){
		this.northWestChild = new QuadTreeNode(new AABB(this.box.getXlow(),this.box.getXmid(),this.box.getYmid(),this.box.getYup()));
		this.northEastChild = new QuadTreeNode(new AABB(this.box.getXmid(),this.box.getXup(),this.box.getYmid(),this.box.getYup()));
		this.southWestChild = new QuadTreeNode(new AABB(this.box.getXlow(),this.box.getXmid(),this.box.getYlow(),this.box.getYmid()));
		this.southEastChild = new QuadTreeNode(new AABB(this.box.getXmid(),this.box.getXup(),this.box.getYlow(),this.box.getYmid()));
	}

}
