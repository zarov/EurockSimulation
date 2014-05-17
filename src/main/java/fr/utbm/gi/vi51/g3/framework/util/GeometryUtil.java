/* 
 * $Id$
 * 
 * Copyright (c) 2007-13 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.utbm.gi.vi51.g3.framework.util;

import javax.vecmath.Vector2d;

/** Geometry Utils.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class GeometryUtil {

	/** Replies the angle between the vector v1 and v2.
	 * <p>
	 * The replied angle is the smallest rotation angle to turn v1 to be equals to
	 * v2. 
	 * <p>
	 * Assumed that: <ul>
	 * <li>signedAngle(v1,v2) == -signedAngle(v2,v2)</li>
	 * <li>v2.equals( turnVector( v1, signedAngle(v1,v2) ) )</li>
	 * </ul>
	 * 
	 * @param v1
	 * @param v2
	 * @return the signed angle between v1 and v2
	 */
	public static double signedAngle(Vector2d v1, Vector2d v2) {
		Vector2d a = new Vector2d(v1);
		if (a.length()==0) return Double.NaN;
		Vector2d b = new Vector2d(v2);
		if (b.length()==0) return Double.NaN;
		a.normalize();
		b.normalize();
		
		double cos = a.x * b.x + a.y * b.y;
		// A x B = |A|.|B|.sin(theta).N = sin(theta) (where N is the unit vector perpendicular to plane AB)
		double sin = a.x*b.y - a.y*b.x;
		
		double angle = Math.atan2(sin, cos);

		return angle;
	}

	/** Clamp the given value v between min and max.
	 * 
	 * @param v
	 * @param min
	 * @param max
	 * @return If v is lower than min, then min.
	 * If v is upper than max, then max. Else v.
	 */
	public static double clamp(double v, double min, double max) {
		if (v<min) return min;
		if (v>max) return max;
		return v;
	}
	
	/** Turn the given vector v about the given angle.
	 * 
	 * @param v
	 * @param angle
	 */
	public static void turnVector(Vector2d v, double angle) {
		double length = v.length();
		if (length==0.) return;
		double currentAngle = signedAngle(new Vector2d(1,0), v);
		currentAngle += angle;
		v.set(Math.cos(currentAngle),Math.sin(currentAngle));
		v.scale(length);
	}
	
	/** Replies the vector which is corresponding to the given angle
	 * 
	 * @param angle
	 * @return the unit vector for which angle = acos(v.x) and angle = asin(v.y)
	 */
	public static Vector2d toOrientationVector(double angle) {
		return new Vector2d(
				Math.cos(angle),
				Math.sin(angle));
	}
	
	/** Compute the angle between the vector (1,0) and the given vector.
	 * 
	 * @param orientation
	 * @return the angle
	 */
	public static double toOrientationAngle(Vector2d orientation) {
		return signedAngle(new Vector2d(1,0), orientation);
	}

}