package fr.utbm.gi.vi51.g3.motion.agent;

import java.util.ArrayList;

import javax.vecmath.Point2d;

public class Schedule {
	private final int day;
	private final ArrayList<String> stage;
	
	public Schedule(){
		this.day=environment.getLengthDay();
		ArrayList<String> stages = environment.getStages();
		for(int i=0;i<this.day;i++){
			this.stage.add(stages.get((int) Math.floor(Math.random() * stages.size())));
		}
	}
	
	public String getPlaceToBe(){
		return stage.get((int) Math.floor(environment.getTime()));
	}
	
}
