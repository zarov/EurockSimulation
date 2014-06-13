package fr.utbm.gi.vi51.g3.motion.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import org.arakhne.afc.vmutil.locale.Locale;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;
import fr.utbm.gi.vi51.g3.framework.environment.Animat;
import fr.utbm.gi.vi51.g3.framework.environment.Environment;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;
import fr.utbm.gi.vi51.g3.motion.behaviour.decisionBehaviour.NeedMessage;
import fr.utbm.gi.vi51.g3.motion.behaviour.decisionBehaviour.NeedType;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.BehaviourOutput;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.FleeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.SeekBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.WanderBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringAlignBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringBehaviourOutput;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringFaceBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringFleeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringSeekBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringWanderBehaviour;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Barrier;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Bomb;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Flora;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.AbstractSmellyObject;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Gate;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stage;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stand;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Toilet;

public class Attendant extends Animat<AgentBody> {
	private static final long serialVersionUID = 4416989095632710549L;

	private final static double SIZE = 0.5;

	private final static double STOP_DISTANCE = 3.;
	private final static double STOP_RADIUS = Math.PI / 10.;
	private final static double SLOW_RADIUS = Math.PI / 4.;
	private final static double WANDER_CIRCLE_DISTANCE = 30.;
	private final static double WANDER_CIRCLE_RADIUS = 30.;
	private final static double WANDER_MAX_ROTATION = Math.PI;
	private final static long PERCEPTION_RANGE = 200;

	private boolean isWaiting;

	private final AttendantGender GENDER;
	private Map<NeedType, Double> needs;

	private final FleeBehaviour<?> fleeBehaviour;
	private final WanderBehaviour<?> wanderBehaviour;
	private final SeekBehaviour<?> seekBehaviour;

	private boolean isOK;

	public Attendant(AttendantGender gender) {
		isOK = true;
		GENDER = gender;
		isWaiting = false;

		initNeeds();

		fleeBehaviour = new SteeringFleeBehaviour();
		seekBehaviour = new SteeringSeekBehaviour();
		SteeringAlignBehaviour alignB = new SteeringAlignBehaviour(STOP_RADIUS,
				SLOW_RADIUS);

		SteeringFaceBehaviour faceB = new SteeringFaceBehaviour(STOP_DISTANCE,
				alignB);
		wanderBehaviour = new SteeringWanderBehaviour(WANDER_CIRCLE_DISTANCE,
				WANDER_CIRCLE_RADIUS, WANDER_MAX_ROTATION, faceB);
	}

	@Override
	public Status activate(Object... activationParameters) {
		Status s = super.activate(activationParameters);
		if (s.isSuccess()) {
			setName(Locale.getString(Attendant.class, GENDER.getName()));
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
		long startTime = System.currentTimeMillis();

		Point2d position = new Point2d(getX(), getY());
		Vector2d orientation = getDirection();
		double linearSpeed = getCurrentLinearSpeed();
		double angularSpeed = getCurrentAngularSpeed();

		Message msg = getMessage();
		if ((msg != null) && (msg instanceof NeedMessage)) {
			satisfyNeed((((NeedMessage) msg).getNeed()),
					((NeedMessage) msg).getAction());
			// System.out.println((((NeedMessage) msg).getNeed()) + " - "
			// + ((NeedMessage) msg).getAction());
		}
		// regularNeedUpdate();

		Class<?> targetClass = computeTargetClass();
		BehaviourOutput output = null;
		Point2d seekTarget = null;
		Point2d fleeTarget = null;
		List<Perception> perceivedObj = getPerceivedObjects();
		double distFromSeekTarget = 3000;
		double distFromFleeTarget = 40;
		double distFromFleeTargetStage = 200;
		double fleeTargetSize = 0;

		for (Perception p : perceivedObj) {
			SituatedObject o = p.getPerceivedObject();
			Vector2d vec = new Vector2d(position);
			vec.sub(o.getPosition());
			double dist = vec.length();
			if (o instanceof Bomb || targetClass == Gate.class) {
				seekTarget = Gate.staticPosition;
				fleeTarget = o.getPosition();
				fleeTargetSize = o.getSize();
				break;
			} else if (isWaiting) {
				// do nothing;
			} else {
				// define the target type in function of the higher need
				if (o.getClass() == targetClass) {
					if (dist < distFromSeekTarget) {
						distFromSeekTarget = dist;
						if (o instanceof Stand) {
							if (dist < 5) {
								isWaiting = true;
								((Stand) o).addNewClient(getAddress());
							}
						}
						// manage gender in case of target being toilets
						if (o instanceof Toilet) {
							if (((Toilet) o).getGender() == GENDER) {
								if (dist < 5) {
									isWaiting = true;
									((Toilet) o).addNewClient(getAddress());
								} else {
									seekTarget = o.getPosition();
								}
							} else {
								// ignore perceived object
							}
						} else {
							seekTarget = o.getPosition();
						}
					}
				}

				// Collision Avoidance
//				if (o instanceof AbstractObstacle
//						|| o instanceof AbstractSmellyObject) {
				if (o instanceof Gate || o instanceof Flora || o instanceof Barrier) {
					if (dist < distFromFleeTarget) {
						distFromFleeTarget = dist;
						fleeTarget = o.getPosition();
						fleeTargetSize = o.getSize();
					}
				}else if(o instanceof Stage)
				{
					if(dist < distFromFleeTargetStage){
						distFromFleeTargetStage = dist;
						fleeTarget = o.getPosition();
						fleeTargetSize = o.getSize();
					}
				}
			}
		}

		if (seekTarget != null) {
			output = seekBehaviour.runSeek(position, linearSpeed,
					getMaxLinearAcceleration(), seekTarget);
		} else if (!isWaiting) {
			output = wanderBehaviour.runWander(position, orientation,
					linearSpeed, getMaxLinearAcceleration(), angularSpeed,
					getMaxAngularAcceleration());
		}
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

		if (output != null) {
			influenceSteering(output.getLinear(), output.getAngular());
		}

		long endTime = System.currentTimeMillis() - startTime;
		// System.out.println(endTime);
		return StatusFactory.ok(this);
	}
//
	// private Point2d getStageNearestSidePoint(Vector2d vec, int width, int
	// height) {
	// Point2d newTarget = new Point2d();
	//
	// // East and West corner
	// if (Math.abs(vec.y) < (height / 2)) {
	// newTarget.x = vec.x + (vec.x > 0 ? -width / 2 : width / 2);
	// newTarget.y = vec.y;
	// // North and South
	// } else if (Math.abs(vec.x) < (width / 2)) {
	// newTarget.x = vec.x;
	// newTarget.y = vec.y + (vec.y > 0 ? -height / 2 : height / 2);
	// }
	//
		// if (vec.getX() > vec.getY()) {
		// if (vec.getX() > 0) {
		// newTarget.x = vec.getX() - (width / 2);
		// } else {
		// newTarget.x = vec.getX() + (width / 2);
		// }
		// newTarget.y = vec.getY() / 2;
		// } else {
		// if (vec.getY() > 0) {
		// newTarget.y = vec.getY() - height;
		// } else {
		// newTarget.y = vec.getY() + height;
		// }
		// newTarget.x = vec.getX() / 2;
		// }
	// return newTarget;
	// }

	private NeedType computeHigherNeed() {
		double val = 0;
		NeedType higherNeed = null;

		for (Entry<NeedType, Double> elem : needs.entrySet()) {
			if (val < elem.getValue()) {
				val = elem.getValue();
				higherNeed = elem.getKey();
			}
		}
		// System.out.println(higherNeed.getName() + " : " + val);
		return higherNeed;
	}

	private Class<? extends AbstractSmellyObject> computeTargetClass() {
		NeedType higherNeed = computeHigherNeed();
		if (higherNeed != null) {
			switch (higherNeed.getName()) {
			case "HUNGER":
				return Stand.class;
			case "THIRST":
				return Stand.class;
			case "PEE":
				return Toilet.class;
			case "SEEGIG":
				return Stage.class;
			case "EXIT":
				return Gate.class;
			default:
				return Stage.class;
			}
		} else {
			return Stage.class;
		}
	}

	public void satisfyNeed(NeedType needType, int action) {
		double newNeedValue = needs.get(needType) + action;
		setNeed(needType, newNeedValue);
		isWaiting = false;
	}

	public void regularNeedUpdate() {
		NeedType need = NeedType.getRandom();
		double newNeedValue = Math.random() + needs.get(need);
		setNeed(need, newNeedValue);
	}

	public void setNeed(NeedType needType, double newValue) {
		if (needType != NeedType.EXIT) {
			if (newValue < 0) {
				newValue = 0;
			} else if (newValue > needType.getMaxValue()) {
				newValue = needType.getMaxValue();
			}
			needs.put(needType, newValue);
		}
	}

	private void initNeeds() {
		needs = new HashMap<NeedType, Double>();
		for (NeedType elem : NeedType.values()) {
			if (elem == NeedType.EXIT) {
				needs.put(elem, 0.);
			} else if (elem == NeedType.SEEGIG) {
				needs.put(elem, 5.);
			} else {
				needs.put(elem, (Math.random() * 10));
			}
		}
	}

	public boolean isOK() {
		return isOK;
	}

	public void hurtAgent() {
		isOK = false;
	}

	public static double getPerceptionRange() {
		return PERCEPTION_RANGE;
	}
}
