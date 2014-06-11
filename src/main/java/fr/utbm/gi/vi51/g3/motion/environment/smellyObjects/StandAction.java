package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;


import fr.utbm.gi.vi51.g3.motion.behaviour.decisionBehaviour.NeedType;

public class StandAction {

	private final NeedType needType;
	// effective action upon need, can be negative or positive value
	private final int action;

	public StandAction(NeedType needType, int action){
		this.needType = needType;
		this.action = action;
	}

	public int getValue() {
		return this.action;
	}

	public NeedType getNeedType() {
		return this.needType;
	}
}
