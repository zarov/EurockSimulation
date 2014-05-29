package fr.utbm.gi.vi51.g3.motion.agent;

public enum WorkerTask {

	MED("MED"), BODYGUARD("BODYGUARD"), SELLER("SELLER");

	public final String name;

	WorkerTask(String name){
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
