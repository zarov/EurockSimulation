package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import java.util.Set;
import java.util.Stack;

import javax.vecmath.Point2d;

import org.janusproject.kernel.address.AgentAddress;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.AbstractSituatedObject;

public class AbstractSmellyObject extends AbstractSituatedObject {

	protected String name;
	private final Set<StandAction> actions;
	private final Stack<AgentAddress> clients;

	public AbstractSmellyObject(double size, Point2d position,
			double orientation, String name, Set<StandAction> actions) {
		super(size, position, orientation);
		this.name = name;
		this.actions = actions;
		clients = new Stack<AgentAddress>();
	}

	public AbstractSmellyObject(double size, Point2d position,
			double orientation, String name) {
		super(size, position, orientation);
		this.name = name;
		actions = null;
		clients = new Stack<AgentAddress>();
	}

	@Override
	public AABB getBox() {
		return new AABB(getX() - (getSize() / 2), getX() + (getSize() / 2),
				getY() - (getSize() / 2), getY() + (getSize() / 2));
	}

	public String getName() {
		return name;
	}

	public AgentAddress getNextClient() {
		if (!clients.isEmpty()) {
			return clients.pop();
		} else {
			return null;
		}
	}

	public void addNewClient(AgentAddress client) {
		clients.push(client);
	}

	public Set<StandAction> getActions() {
		return actions;
	}
}
