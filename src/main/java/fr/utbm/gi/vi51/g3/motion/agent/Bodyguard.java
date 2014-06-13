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
import fr.utbm.gi.vi51.g3.framework.environment.Perception;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.BehaviourOutput;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.FleeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.WanderBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringAlignBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringFaceBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringFleeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringWanderBehaviour;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Bomb;

public class Bodyguard extends Animat<AgentBody> {

	private static final long serialVersionUID = 6325181339385994809L;

	private final static double SIZE = 0.5;

	private final static double STOP_DISTANCE = 3.;
	private final static double STOP_RADIUS = Math.PI / 10.;
	private final static double SLOW_RADIUS = Math.PI / 4.;
	private final static double WANDER_CIRCLE_DISTANCE = 30.;
	private final static double WANDER_CIRCLE_RADIUS = 30.;
	private final static double WANDER_MAX_ROTATION = Math.PI;
	private final static long PERCEPTION_RANGE = 200;
	private boolean isOK;
	private final FleeBehaviour<?> fleeBehaviour;
	private final WanderBehaviour<?> wanderBehaviour;

	public Bodyguard() {
		fleeBehaviour = new SteeringFleeBehaviour();
		SteeringAlignBehaviour alignB = new SteeringAlignBehaviour(STOP_RADIUS,
				SLOW_RADIUS);

		SteeringFaceBehaviour faceB = new SteeringFaceBehaviour(STOP_DISTANCE,
				alignB);
		wanderBehaviour = new SteeringWanderBehaviour(WANDER_CIRCLE_DISTANCE,
				WANDER_CIRCLE_RADIUS, WANDER_MAX_ROTATION, faceB);
		isOK=true;
	}

	@Override
	public Status activate(Object... activationParameters) {
		Status s = super.activate(activationParameters);
		if (s.isSuccess()) {
			setName(Locale.getString(Bodyguard.class, "BODYGUARD"));
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

		BehaviourOutput output = null;
		List<Perception> perceptions = getPerceivedObjects();

		if (!perceptions.isEmpty()) {
			for (Perception p : perceptions) {
				if (p.getPerceivedObject() instanceof Bomb) {
					output = fleeBehaviour.runFlee(position, linearSpeed, 0.5,
							p.getPerceivedObject().getPosition());
				}
			}
		} else {
			// if no object is perceived then wander
			output = this.wanderBehaviour.runWander(position,
					orientation, linearSpeed, getMaxLinearAcceleration(),
					angularSpeed, getMaxAngularAcceleration());
		}

		// send influences to the environment
		if (output != null) {
			influenceSteering(output.getLinear(), output.getAngular());
		}

		return StatusFactory.ok(this);
	}

	public boolean isOK() {
		return isOK;
	}
	
	public void hurtAgent(){
		this.isOK=false;
	}
	
	public static double getPerceptionRange() {
		return PERCEPTION_RANGE;
	}
}
