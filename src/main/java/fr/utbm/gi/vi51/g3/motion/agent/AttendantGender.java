package fr.utbm.gi.vi51.g3.motion.agent;

public enum AttendantGender {

	MAN("MAN"), WOMAN("WOMAN");

	public final String name;

	AttendantGender(String name){
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
