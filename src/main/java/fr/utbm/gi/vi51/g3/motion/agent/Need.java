package fr.utbm.gi.vi51.g3.motion.agent;

public class Need {

	private final NeedType type;
	private int value;

	Need(NeedType type, int value){
		this.type = type;
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public NeedType getType() {
		return this.type;
	}
}
