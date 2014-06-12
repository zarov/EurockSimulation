package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import java.util.Set;

import javax.vecmath.Point2d;

import fr.utbm.gi.vi51.g3.motion.agent.AttendantGender;

public class Toilet extends AbstractSmellyObject {

	private final AttendantGender gender;

	public Toilet(double size, Point2d position, double orientation,
			String name,
 AttendantGender gender, Set<StandAction> actions) {
		super(size, position, orientation, name, actions);
		this.gender = gender;
	}

	public AttendantGender getGender() {
		return gender;
	}

	public String getName() {
		return name + "_" + gender.getName();

	}


}