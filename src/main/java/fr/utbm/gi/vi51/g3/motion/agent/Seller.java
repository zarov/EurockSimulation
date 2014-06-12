package fr.utbm.gi.vi51.g3.motion.agent;

import java.util.List;

import javax.vecmath.Point2d;

import org.arakhne.afc.vmutil.locale.Locale;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;
import fr.utbm.gi.vi51.g3.framework.environment.Animat;
import fr.utbm.gi.vi51.g3.framework.environment.Environment;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.BehaviourOutput;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.FleeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.SeekBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringFleeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringSeekBehaviour;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Bomb;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stand;

public class Seller extends Animat<AgentBody> {

	private static final long serialVersionUID = 4416989095632710549L;

	private final static double SIZE = 0.5;

	private final static long PERCEPTION_RANGE = 10;

	private final FleeBehaviour<?> fleeBehaviour;
	private final SeekBehaviour<?> seekBehaviour;
	private final Stand stand;

	public Seller(Stand s) {
		stand = s;
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

		double dist = 1000;
		Attendant personToServe = null;

		for (Perception p : perc) {
			if (p.getPerceivedObject() instanceof Bomb) {
				output = fleeBehaviour.runFlee(position, linearSpeed, 0.5, p
						.getPerceivedObject().getPosition());

			} else if (p.getPerceivedObject() instanceof AgentBody) {
				System.out.println(((AgentBody) p.getPerceivedObject())
						.getOwner().getName());
				//
				// Vector2d vec = new Vector2d(position);
				// vec.sub(p.getPerceivedObject().getPosition());
				// if (vec.length() < dist) {
				// dist = vec.length();
				// personToServe = (Attendant) p.getPerceivedObject();
				// }
			}
		}

		if (personToServe != null) {
			personToServe.satisfyNeed(stand.getStandAction().getNeedType(),
					stand.getStandAction().getValue());
		}

		if (output == null) {
			output = seekBehaviour.runSeek(position, linearSpeed, 0.5,
					stand.getPosition());
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

}
