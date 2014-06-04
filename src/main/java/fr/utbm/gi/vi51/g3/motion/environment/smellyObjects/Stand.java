package fr.utbm.gi.vi51.g3.motion.environment.smellyObjects;

import java.util.Queue;

import javax.vecmath.Point2d;

import fr.utbm.gi.vi51.g3.motion.agent.Attendant;

public class Stand extends AbstractSmellyObject {

	private final String name;
	//TODO enum pour les types de stand ?
	private final StandAction action;
	private final Queue<Attendant> clients;

	public Stand(double size, Point2d position, double orientation, String name, StandAction action) {
		super(size, position, orientation);
		this.action = action;
		this.name = name;
		this.clients = null;
	}
	

	public String getName() {
		return this.name;
	}

	public StandAction getAction() {
		return this.action;
	}

	public void newClient(Attendant a) {
		if(a.hasNeed(this.action.getNeedType())){
			this.clients.add(a);
		}
	}
	
	public void serveClient(){
		Attendant a = this.clients.remove();
		a.updateNeeds(this.action.getNeedType());
	}
}
