package fr.utbm.gi.vi51.g3.motion.agent;

public enum NeedType {

	HUNGER("HUNGER", 10), THIRST("THIRST", 10), PEE("PEE", 10), SEEGIG("SEEGIG", 10), EXIT("EXIT", 1);

	public final String name;
	public final int MAX_VALUE;

	private NeedType(String name, int maxValue){
		this.name = name;
		this.MAX_VALUE = maxValue;
	}

	public String getName(String name) {
		return this.name;
	}

	public int getValue(int value) {
		return this.MAX_VALUE;
	}

}
