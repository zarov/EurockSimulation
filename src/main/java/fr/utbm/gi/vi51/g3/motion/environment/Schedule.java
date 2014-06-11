package fr.utbm.gi.vi51.g3.motion.environment;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Plan;

public class Schedule {
	private final List<Plan> gigs;

	public Schedule() {
		gigs = new ArrayList<>();
		Plan[] stages = Plan.values();
		// for (Plan stage : stages) {
		// gigs.add((int) (Math.floor(Math.random() * stages.length)), stage);
		// }
	}

	/**
	 * Get the stage to be to.
	 * 
	 * @return
	 */
	public Plan getPlaceToBe() {
		return gigs.get(0);
	}

	/**
	 * Remove the stage of the schedule
	 */
	public void removePlaceToBe() {
		gigs.remove(0);
	}
}
