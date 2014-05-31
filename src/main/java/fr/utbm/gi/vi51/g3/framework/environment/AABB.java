package fr.utbm.gi.vi51.g3.framework.environment;

/**
 * An implementation of an Axis-Aligned minimum Bounding Box.
 *
 * @author zarov
 *
 */
public class AABB {

	private final double xlow;
	private final double xup;
	private final double ylow;
	private final double yup;

	public AABB(double xlow, double xup, double ylow, double yup) {
		this.xlow = xlow;
		this.xup = xup;
		this.ylow = ylow;
		this.yup = yup;
	}

	public double getXlow() {
		return xlow;
	}

	public double getXup() {
		return xup;
	}

	public double getYlow() {
		return ylow;
	}

	public double getYup() {
		return yup;
	}

	public double getXmid() {
		return xlow + ((xup - xlow) / 2);
	}

	public double getYmid() {
		return ylow + ((yup - ylow) / 2);
	}

	/**
	 * Compares two boxes, frustum and this one, and tell if they intersect.
	 *
	 * @param frustrum
	 * @return true or false
	 */
	public boolean intersects(AABB frustrum) {
		return !((frustrum.xup < xlow) || (frustrum.xlow > xup)
				|| (frustrum.yup < ylow) || (frustrum.ylow > yup));
	}

	/**
	 * Compares an object and this box, and tell if the object is inside the
	 * box.
	 *
	 * @param object
	 * @return true or false
	 */
	public boolean contains(SituatedObject object) {
		return !((object.getX() < xlow) || (object.getX() > xup)
				|| (object.getY() < ylow) || (object.getY() > yup));
	}

	@Override
	public String toString() {
		return "A(" + xlow + ", " + ylow + ") et B(" + xup + ", " + yup + ")";
	}

}
