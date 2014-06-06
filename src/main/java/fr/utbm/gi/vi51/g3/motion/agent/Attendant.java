package fr.utbm.gi.vi51.g3.motion.agent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import org.arakhne.afc.vmutil.locale.Locale;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;
import fr.utbm.gi.vi51.g3.framework.environment.Animat;
import fr.utbm.gi.vi51.g3.framework.environment.Environment;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.FleeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.SeekBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.WanderBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringAlignBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringFaceBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringFleeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringSeekBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringWanderBehaviour;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Bomb;

public class Attendant extends Animat<AgentBody> {
	private static final long serialVersionUID = 4416989095632710549L;

	private final static double SIZE = 20.;

	private final static double STOP_DISTANCE = 3.;
	private final static double STOP_RADIUS = Math.PI / 10.;
	private final static double SLOW_RADIUS = Math.PI / 4.;
	private final static double WANDER_CIRCLE_DISTANCE = 30.;
	private final static double WANDER_CIRCLE_RADIUS = 30.;
	private final static double WANDER_MAX_ROTATION = Math.PI;
	private final static long PERCEPTION_RANGE = 200;

	private final boolean isOK;

	private final AttendantGender GENDER;
	private Set<Need> needs;

	private final FleeBehaviour<?> fleeBehaviour;
	private final WanderBehaviour<?> wanderBehaviour;
	private final SeekBehaviour<?> seekBehaviour;

	public Attendant(AttendantGender gender) {
		isOK = true;
		// if (this.isSteering) {
		initNeeds();
		GENDER = gender;
		fleeBehaviour = new SteeringFleeBehaviour();
		seekBehaviour = new SteeringSeekBehaviour();
		SteeringAlignBehaviour alignB = new SteeringAlignBehaviour(STOP_RADIUS,
				SLOW_RADIUS);

		SteeringFaceBehaviour faceB = new SteeringFaceBehaviour(STOP_DISTANCE,
				alignB);
		wanderBehaviour = new SteeringWanderBehaviour(WANDER_CIRCLE_DISTANCE,
				WANDER_CIRCLE_RADIUS, WANDER_MAX_ROTATION, faceB);

		// } else {
		// this.evadeBehaviour = new KinematicEvadeBehaviour();
		// this.wanderBehaviour = new KinematicWanderBehaviour();
		// }

	}

	private void initNeeds() {
		needs = new HashSet<Need>();
		for (NeedType elem : NeedType.values()) {
			needs.add(new Need(elem, (int) Math.random() * 10));
		}
	}

	@Override
	public Status activate(Object... activationParameters) {
		Status s = super.activate(activationParameters);
		if (s.isSuccess()) {
			setName(Locale.getString(Attendant.class, GENDER.getName()));
			System.out.println(getName());
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
			SituatedObject o = p.getPerceivedObject();
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

	public boolean isOK() {
		return isOK;
	}

	public boolean isAttendant() {
		return true;
	}

	public boolean hasNeed(NeedType needType) {
		for (Need n : needs) {
			if ((n.getType() == needType) && (n.getValue() == 10)) {
				return true;
			}

		}
		return false;
	}

	public void updateNeeds(NeedType needType) {
		for (Need n : needs) {
			if ((n.getType() == needType) && (n.getValue() == 10)) {
				n.setValue(0);
			}
		}

	}

}
