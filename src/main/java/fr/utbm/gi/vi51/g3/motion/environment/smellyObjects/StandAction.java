package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;


import fr.utbm.gi.vi51.g3.motion.agent.NeedType;

public class StandAction {

	private final NeedType needType;
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
