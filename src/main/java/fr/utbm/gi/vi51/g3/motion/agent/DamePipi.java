package fr.utbm.gi.vi51.g3.motion.agent;

import java.util.Set;

import org.arakhne.afc.vmutil.locale.Locale;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;
import fr.utbm.gi.vi51.g3.framework.environment.Animat;
import fr.utbm.gi.vi51.g3.framework.environment.Environment;
import fr.utbm.gi.vi51.g3.motion.behaviour.decisionBehaviour.NeedMessage;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.StandAction;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Toilet;

public class DamePipi extends Animat<AgentBody> {

	private static final long serialVersionUID = 4416989095632710549L;
	
	private final Set<Toilet> toiletSet;
	
	public DamePipi(Set<Toilet> toiletSet) {
		this.toiletSet = toiletSet;
	}

	@Override
	public Status activate(Object... activationParameters) {
		Status s = super.activate(activationParameters);
		if (s.isSuccess()) {
			setName(Locale.getString(DamePipi.class, "INVISIBLE")); //$NON-NLS-1$
		}
		return s;
	}

	@Override
	protected AgentBody createBody(Environment in) {
		return new AgentBody(getAddress(), 0, 5, // max linear speed m/s
				.5, // max linear acceleration (m/s)/s
				Math.PI / 4, // max angular speed r/s
				Math.PI / 10, 0); // max angular acceleration
													// (r/s)/s
	}

	@Override
	public Status live() {

		for (Toilet toilet : toiletSet) {
			AgentAddress clientToServe = toilet.getNextClient();
			if (clientToServe != null) {
				for (StandAction action : toilet.getActions()) {
					NeedMessage needMsg = new NeedMessage(action.getNeedType(),
							action.getValue());
					sendMessage(needMsg, clientToServe);
				}
			}
		}

		return StatusFactory.ok(this);
	}
}