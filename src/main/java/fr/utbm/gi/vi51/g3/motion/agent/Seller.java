package fr.utbm.gi.vi51.g3.motion.agent;

import java.util.List;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import org.arakhne.afc.vmutil.locale.Locale;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;
import fr.utbm.gi.vi51.g3.framework.environment.Animat;
import fr.utbm.gi.vi51.g3.framework.environment.Environment;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;
import fr.utbm.gi.vi51.g3.motion.behaviour.decisionBehaviour.NeedMessage;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.BehaviourOutput;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.FleeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.SeekBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringBehaviourOutput;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringFleeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringSeekBehaviour;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Bomb;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Gate;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stand;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.StandAction;

public class Seller extends Animat<AgentBody> {

	private static final long serialVersionUID = 4416989095632710549L;

	private final static double SIZE = 0.5;

	private final static long PERCEPTION_RANGE = 5;

	private final FleeBehaviour<?> fleeBehaviour;
	private final SeekBehaviour<?> seekBehaviour;
	private final Stand stand;
	
	private boolean isOK;

	public Seller(Stand s) {
		stand = s;
		isOK = true;
		fleeBehaviour = new SteeringFleeBehaviour();
		seekBehaviour = new SteeringSeekBehaviour();
	}

	@Override
	public Status activate(Object... activationParameters) {
		Status s = super.activate(activationParameters);
		if (s.isSuccess()) {
			setName(Locale.getString(Seller.class, "SELLER")); //$NON-NLS-1$
		}
		return s;
	}

	@Override
	protected AgentBody createBody(Environment in) {
		return new AgentBody(getAddress(), SIZE, 5, // max linear speed m/s
				.5, // max linear acceleration (m/s)/s
				Math.PI / 4, // max angular speed r/s
				Math.PI / 10, PERCEPTION_RANGE); // max angular acceleration
													// (r/s)/s
	}

	@Override
	public Status live() {
		Point2d position = new Point2d(getX(), getY());
		double linearSpeed = getCurrentLinearSpeed();

		List<Perception> perc = getPerceivedObjects();
		BehaviourOutput output = null;

		Point2d seekTarget = null;
		Point2d fleeTarget = null;
		double fleeTargetSize = 0;

		for (Perception p : perc) {
			if (p.getPerceivedObject() instanceof Bomb) {
				SituatedObject bomb = p.getPerceivedObject();
				seekTarget = Gate.staticPosition;
				fleeTarget = bomb.getPosition();
				fleeTargetSize = bomb.getSize();
			} else {
				AgentAddress clientToServe = stand.getNextClient();
				if (clientToServe != null) {
					for (StandAction action : stand.getActions()) {
						NeedMessage needMsg = new NeedMessage(
								action.getNeedType(), action.getValue());
						sendMessage(needMsg, clientToServe);
					}
				}
			}
		}
		if (seekTarget == null) {
			seekTarget = stand.getPosition();
		}
		output = seekBehaviour.runSeek(position, linearSpeed, 0.5, seekTarget);
		if (fleeTarget != null) {
			BehaviourOutput negativeOutput = fleeBehaviour.runFlee(position,
					linearSpeed, getMaxLinearAcceleration(), fleeTarget);
			negativeOutput.getLinear().normalize();
			negativeOutput.getLinear().scale(fleeTargetSize / 4);
			Vector2d newLinear;
			if (output != null) {
				newLinear = output.getLinear();
				newLinear.add(negativeOutput.getLinear());
				newLinear.normalize();
				newLinear.scale(getMaxLinearAcceleration());
				output.setLinear(newLinear);
			} else {
				output = new SteeringBehaviourOutput();
				output.setLinear(negativeOutput.getLinear());
			}
		}

		influenceSteering(output.getLinear(), output.getAngular());

		return StatusFactory.ok(this);
	}

	public static double getPerceptionRange() {
		return PERCEPTION_RANGE;
	}

	public Stand getStand() {
		return stand;
	}
	
	public boolean isOK() {
		return isOK;
	}
	
	public void hurtAgent(){
		this.isOK=false;
	}

}
