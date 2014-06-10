package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import java.util.Queue;

import javax.vecmath.Point2d;

import fr.utbm.gi.vi51.g3.motion.agent.Attendant;

public class Stand extends AbstractSmellyObject {

	private final StandAction action;
	private final Queue<Attendant> clients;

	public Stand(double size, Point2d position, double orientation, String name, StandAction action) {
		super(size, position, orientation, name);
		this.action = action;
		this.clients = null;
	}

	public StandAction getAction() {
		return this.action;
	}

	public void newClient(Attendant a) {
		this.clients.add(a);
	}
	
	public void serveClient(){
		Attendant a = this.clients.remove();
		a.satisfyNeed(this.action.getNeedType(), this.action.getValue());
	}
}
