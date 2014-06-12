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
import fr.utbm.gi.vi51.g3.motion.environment.Schedule;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Bomb;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.AbstractSmellyObject;
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
	private Map<NeedType, Integer> needs;

	private final FleeBehaviour<?> fleeBehaviour;
	private final WanderBehaviour<?> wanderBehaviour;
	private final SeekBehaviour<?> seekBehaviour;

	private final Schedule sched;

	private boolean isOK;

	public Attendant(AttendantGender gender) {
		this.isOK = true;
		GENDER = gender;
		isWaiting = false;

		initNeeds();
		sched = new Schedule();

		fleeBehaviour = new SteeringFleeBehaviour();
		seekBehaviour = new SteeringSeekBehaviour();
		SteeringAlignBehaviour alignB = new SteeringAlignBehaviour(STOP_RADIUS,
				SLOW_RADIUS);

		SteeringFaceBehaviour faceB = new SteeringFaceBehaviour(STOP_DISTANCE,
				alignB);
		wanderBehaviour = new SteeringWanderBehaviour(WANDER_CIRCLE_DISTANCE,
				WANDER_CIRCLE_RADIUS, WANDER_MAX_ROTATION, faceB);
	}

	private void initNeeds() {
		needs = new HashMap<NeedType, Integer>();
		for (NeedType elem : NeedType.values()) {
			if (elem == NeedType.EXIT) {
				needs.put(elem, 0);
			} else if (elem == NeedType.SEEGIG) {
				needs.put(elem, 5);
			} else {
				needs.put(elem, (int) (Math.random() * 10));
			}
		}
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
		Message msg = getMessage();
		if (msg != null && msg instanceof NeedMessage) {
			satisfyNeed((((NeedMessage) msg).getNeed()),
					((NeedMessage) msg).getAction());
		}

		Point2d position = new Point2d(getX(), getY());
		Vector2d orientation = getDirection();
		double linearSpeed = getCurrentLinearSpeed();
		double angularSpeed = getCurrentAngularSpeed();

		BehaviourOutput output = null;
		List<Perception> perceivedObj = getPerceivedObjects();
		double distFromTarget = 3000;

		for (Perception p : perceivedObj) {
			SituatedObject o = p.getPerceivedObject();
			Vector2d vec = new Vector2d(position);
			vec.sub(o.getPosition());
			if (o instanceof Bomb) {
				if (vec.length() < PERCEPTION_RANGE) {
					output = fleeBehaviour.runFlee(position, linearSpeed, 0.5,
						o.getPosition());
					break;
				}
			} else if (!isWaiting) {
				// define the target type in function of the higher need
				if (o.getClass() == computeTargetClass()) {
					if (vec.length() < distFromTarget) {
						distFromTarget = vec.length();	
						if(o instanceof Stand){
							if(distFromTarget < 5){
								isWaiting = true;
								((Stand) o).addNewClient(getAddress());
							}
						} 
						// manage gender in case of target being toilets
						if (o instanceof Toilet) {
							if (((Toilet) o).getGender() == this.GENDER) {
								if(distFromTarget < 5){
									isWaiting = true;
									((Toilet) o).addNewClient(getAddress());
								} else {
									output = seekBehaviour.runSeek(position,
											linearSpeed, 0.5, o.getPosition());
								}
							} else {
								// ignore perceived object
							}
						} else {
							output = seekBehaviour.runSeek(position,
									linearSpeed, 0.5, o.getPosition());
						}
					}
				}

				if (output == null) {
					output = wanderBehaviour.runWander(position, orientation,
							linearSpeed, getMaxLinearAcceleration(),
							angularSpeed, getMaxAngularAcceleration());
				}

				if (o instanceof Stage) {
					BehaviourOutput negativeOutput = fleeBehaviour.runFlee(
							position, linearSpeed, getMaxLinearAcceleration(),
							o.getPosition());
					negativeOutput.getLinear().normalize();
					negativeOutput.getLinear().scale(((Stage) o).getWidth());
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
			}
		}

		// getStageNearestSidePoint(vec,
		// ((Stage) o).getSizeX(),
		// ((Stage) o).getSizeY())
		// Plan placeToBe = sched.getPlaceToBe();
		// Stage stageToBe = new Stage(placeToBe.size, placeToBe.position,
		// placeToBe.direction, placeToBe.name);
		// if (!stageToBe.isInRange(position)) {
		// if (stageToBe.isOnAir()) {
		// output = seekBehaviour.runSeek(position, linearSpeed, 0.5,
		// stageToBe.getPosition());
		// } else {
		// output = wanderBehaviour.runWander(position, orientation,
		// linearSpeed,
		// 0.5, angularSpeed, Math.PI / 4);
		// }
		// } else if (!stageToBe.isOnAir()) {
		// output = wanderBehaviour.runWander(position, orientation,
		// linearSpeed, 0.5,
		// angularSpeed, Math.PI / 4);
		// }

		if (output != null) {
			influenceSteering(output.getLinear(), output.getAngular());
		}
		return StatusFactory.ok(this);
	}

	private Point2d getStageNearestSidePoint(Vector2d vec, int width, int height) {
		Point2d newTarget = new Point2d();

		if (vec.getX() > vec.getY()) {
			if (vec.getX() > 0) {
				newTarget.x = vec.getX() - width / 2;
			} else {
				newTarget.x = vec.getX() + width / 2;
			}
			newTarget.y = vec.getY() / 2;
		} else {
			if (vec.getY() > 0) {
				newTarget.y = vec.getY() - height;
			} else {
				newTarget.y = vec.getY() + height;
			}
			newTarget.x = vec.getX() / 2;
		}
		return newTarget;
	}

	private NeedType computeHigherNeed() {
		int val = 0;
		NeedType higherNeed = null;

		for (Entry<NeedType, Integer> elem : needs.entrySet()) {
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
			// return Exit.class;
			default :
				return Stage.class;
			}
		} else
			return Stage.class;
	}

	public void satisfyNeed(NeedType needType, int action) {
		int newNeedValue = needs.get(needType) + action;
		if (newNeedValue < 0) {
			newNeedValue = 0;
		}
		System.out.println(newNeedValue);
		needs.put(needType, newNeedValue);
		isWaiting = false;
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
