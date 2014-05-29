package fr.utbm.gi.vi51.g3.motion.agent;

import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import org.arakhne.afc.vmutil.locale.Locale;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;
import fr.utbm.gi.vi51.g3.framework.environment.Animat;
import fr.utbm.gi.vi51.g3.framework.environment.Environment;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.EvadeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.WanderBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringAlignBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringEvadeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringFaceBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringWanderBehaviour;

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

	private final EvadeBehaviour<?> evadeBehaviour;
	private final WanderBehaviour<?> wanderBehaviour;

	public Attendant(AttendantGender gender) {
		this.isOK = true;
		// if (this.isSteering) {
		initNeeds();
		this.GENDER = gender;
		this.evadeBehaviour = new SteeringEvadeBehaviour();
		SteeringAlignBehaviour alignB = new SteeringAlignBehaviour(STOP_RADIUS,
				SLOW_RADIUS);

		SteeringFaceBehaviour faceB = new SteeringFaceBehaviour(STOP_DISTANCE,
				alignB);
		this.wanderBehaviour = new SteeringWanderBehaviour(
				WANDER_CIRCLE_DISTANCE, WANDER_CIRCLE_RADIUS,
				WANDER_MAX_ROTATION, faceB);
		// } else {
		// this.evadeBehaviour = new KinematicEvadeBehaviour();
		// this.wanderBehaviour = new KinematicWanderBehaviour();
		// }

	}

	private void initNeeds() {
		this.needs = new HashSet<Need>();
		for (NeedType elem : NeedType.values()) {
			this.needs.add(new Need(elem, (int) Math.random() * 10));
		}
	}

	@Override
	public Status activate(Object... activationParameters) {
		Status s = super.activate(activationParameters);
		if (s.isSuccess()) {
			setName(Locale.getString(Attendant.class, this.GENDER.getName()));
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

		// BehaviourOutput globalOutput = null;
		// List<Perception> perceptions = getPerceivedObjects();
		//
		// if(!perceptions.isEmpty()) {
		// for(Perception percept: perceptions)
		// {
		//
		// }
		// } else {
		//
		// //if no object is perceived then wander
		// globalOutput = this.wanderBehaviour.runWander(position, orientation,
		// linearSpeed, getMaxLinear(), angularSpeed, getMaxAngular());
		// }
		//
		// //send influences to the environment
		// if (globalOutput!=null) {
		// if (this.isSteering)
		// influenceSteering(globalOutput.getLinear(),globalOutput.getAngular());
		// else
		// influenceKinematic(globalOutput.getLinear(),globalOutput.getAngular());
		// }
		//
		return StatusFactory.ok(this);
	}

	// private double getMaxLinear() {
	// return this.isSteering ? getMaxLinearAcceleration() :
	// getMaxLinearSpeed();
	// }
	//
	// private double getMaxAngular() {
	// return this.isSteering ? getMaxAngularAcceleration() :
	// getMaxAngularSpeed();
	// }

	public static double getPerceptionRange() {
		return PERCEPTION_RANGE;
	}

	public boolean isOK() {
		return this.isOK;
	}

	public boolean isAttendant() {
		return true;
	}

}
