package fr.utbm.gi.vi51.g3.motion.behaviour.decisionBehaviour;

import org.janusproject.kernel.message.Message;

public class NeedMessage extends Message{

	private static final long serialVersionUID = 502047584443361649L;
	
	private final NeedType need;
	private final int action;

	public NeedMessage(NeedType need, int action){
		this.need = need;
		this.action = action;
	}

	public int getAction() {
		return action;
	}

	public NeedType getNeed() {
		return need;
	}

}
