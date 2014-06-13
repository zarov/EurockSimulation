package fr.utbm.gi.vi51.g3.motion.agent;

import java.util.List;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import org.arakhne.afc.vmutil.locale.Locale;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

import fr.utbm.gi.vi51.g3.framework.environment.Perception;
import fr.utbm.gi.vi51.g3.framework.environment.SituatedObject;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.BehaviourOutput;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.FleeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.SeekBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringAlignBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringBehaviourOutput;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringFaceBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringFleeBehaviour;
import fr.utbm.gi.vi51.g3.motion.behaviour.motionBehaviour.steering.SteeringSeekBehaviour;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Barrier;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Bomb;
import fr.utbm.gi.vi51.g3.motion.environment.obstacles.Flora;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Gate;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stage;

public class Bodyguard extends Vigil {

	private static final long serialVersionUID = 6325181339385994809L;

	private final static double STOP_DISTANCE = 3.;
	private final static double STOP_RADIUS = Math.PI / 10.;
	private final static double SLOW_RADIUS = Math.PI / 4.;
	private final static int MAX_LINEAR = 5;
	private final static double MAX_ANGULAR = 0.5;
	private boolean isOK;
	private final FleeBehaviour<?> fleeBehaviour;
	private final SeekBehaviour<?> seekBehaviour;
	private final Stage stage;
	private final int index;

	public Bodyguard(Stage s, int i) {
		fleeBehaviour = new SteeringFleeBehaviour();
		SteeringAlignBehaviour alignB = new SteeringAlignBehaviour(STOP_RADIUS,
				SLOW_RADIUS);

		SteeringFaceBehaviour faceB = new SteeringFaceBehaviour(STOP_DISTANCE,
				alignB);
		seekBehaviour = new SteeringSeekBehaviour();
		isOK = true;
		stage = s;
		index = i;
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
	public Status live() {
		Point2d position = new Point2d(getX(), getY());
		Vector2d orientation = getDirection();
		double linearSpeed = getCurrentLinearSpeed();
		double angularSpeed = getCurrentAngularSpeed();
		
		Point2d seekTarget = null;
		Point2d fleeTarget = null;
		double fleeTargetSize = 0;
		double distFromSeekTarget = 3000;
		double distFromFleeTarget = 40;

		BehaviourOutput output = null;
		List<Perception> perceptions = getPerceivedObjects();

		if (!perceptions.isEmpty()) {
			for (Perception p : perceptions) {
				SituatedObject o = p.getPerceivedObject();
				Vector2d vec = new Vector2d(position);
				vec.sub(o.getPosition());
				double dist = vec.length();
				if (o instanceof Bomb) {
					output = fleeBehaviour.runFlee(position, linearSpeed, 0.5,
							p.getPerceivedObject().getPosition());
				}
				else if (o instanceof Stage || o instanceof Gate || o instanceof Flora || o instanceof Barrier) {
					if (dist < distFromFleeTarget) {
						distFromFleeTarget = dist;
						fleeTarget = o.getPosition();
						fleeTargetSize = o.getSize();
					}
				}
			}
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
		
		if(output == null)
		{
			Point2d placeToBe = new Point2d(stage.getX()+(index*5),stage.getY());
			if(placeToBe != position)
				output = seekBehaviour.runSeek(position, linearSpeed, MAX_LINEAR, placeToBe);
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

	public void hurtAgent() {
		isOK = false;
	}

	public static double getPerceptionRange() {
		return PERCEPTION_RANGE;
	}
}
