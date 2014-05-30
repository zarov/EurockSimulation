package fr.utbm.gi.vi51.g3.framework.environment;


/**
 * @author zarov
 *
 */
public class AABB {

	private final double xlow;
	private final double xup;
	private final double ylow;
	private final double yup;

	public AABB(double xlow, double xup, double ylow, double yup){
		this.xlow = xlow;
		this.xup = xup;
		this.ylow = ylow;
		this.yup = yup;
	}

	public double getXlow() {
		return this.xlow;
	}

	public double getXup() {
		return this.xup;
	}

	public double getYlow() {
		return this.ylow;
	}

	public double getYup() {
		return this.yup;
	}

	public double getXmid() {
		return this.xlow + ((this.xup-this.xlow)/2);
	}

	public double getYmid(){
		return this.ylow + ((this.yup-this.ylow)/2);
	}

	public boolean intersects(AABB frustrum) {
		return !(frustrum.xup < this.xlow
				|| frustrum.xlow > this.xup
				|| frustrum.yup < this.ylow
				|| frustrum.ylow > this.yup);
	}


	public boolean contains(SituatedObject object) {
		return !(object.getX() < this.xlow
				|| object.getX() > this.xup
				|| object.getY() < this.ylow
				|| object.getY() > this.yup);
	}

}
