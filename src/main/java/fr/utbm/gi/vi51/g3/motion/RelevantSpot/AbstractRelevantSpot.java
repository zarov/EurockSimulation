package fr.utbm.gi.vi51.g3.motion.RelevantSpot;

import javax.vecmath.Point2d;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.AbstractSituatedObject;

public class AbstractRelevantSpot extends AbstractSituatedObject{

	public AbstractRelevantSpot(double size) {
		super(size);
		// TODO Auto-generated constructor stub
	}
	
	public AbstractRelevantSpot(double size, Point2d position,
			double orientation) {
		super(size, position, orientation);
		// TODO Auto-generated constructor stub
	}


	@Override
	public AABB getBox() {
		// TODO Auto-generated method stub
		return null;
	}

}
