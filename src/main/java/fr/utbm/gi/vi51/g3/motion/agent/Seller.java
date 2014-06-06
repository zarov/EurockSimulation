package fr.utbm.gi.vi51.g3.motion.agent;

import java.util.List;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import org.arakhne.afc.vmutil.locale.Locale;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;
import fr.utbm.gi.vi51.g3.framework.environment.Animat;
import fr.utbm.gi.vi51.g3.framework.environment.Environment;
import fr.utbm.gi.vi51.g3.framework.environment.MobileObject;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.FleeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringFleeBehaviour;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Bomb;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stand;

public class Seller extends Animat<AgentBody> {

	private static final long serialVersionUID = 4416989095632710549L;

	private final static double SIZE = 20.;

	private final static long PERCEPTION_RANGE = 200;

	private final FleeBehaviour<?> fleeBehaviour;
	private final Stand stand;

	public Seller(Stand s) {
		stand = s;
		fleeBehaviour = new SteeringFleeBehaviour();
	}

	@Override
	public Status activate(Object... activationParameters) {
		Status s = super.activate(activationParameters);
		if (s.isSuccess()) {
			setName(Locale.getString(Medic.class, "MEDIC")); //$NON-NLS-1$
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
		Vector2d orientation = getDirection();
		double linearSpeed = getCurrentLinearSpeed();
		double angularSpeed = getCurrentAngularSpeed();

		List<Perception> perc = getPerceivedObjects();

		for (Perception p : perc) {
			MobileObject o = (MobileObject) p.getPerceivedObject();
			if (o instanceof Bomb) {
				fleeBehaviour.runFlee(position, linearSpeed, 0.5,
						o.getPosition());
			}
		}

		return StatusFactory.ok(this);
	}

	public static double getPerceptionRange() {
		return PERCEPTION_RANGE;
	}

}
