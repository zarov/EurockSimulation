package fr.utbm.gi.vi51.g3.framework.tree;

import java.util.List;

import org.junit.Test;

import fr.utbm.gi.vi51.g3.framework.environment.AABB;
import fr.utbm.gi.vi51.g3.framework.environment.AgentBody;
import fr.utbm.gi.vi51.g3.framework.environment.Perception;
import fr.utbm.gi.vi51.g3.motion.agent.Attendant;
import fr.utbm.gi.vi51.g3.motion.agent.AttendantGender;
import fr.utbm.gi.vi51.g3.motion.agent.Bodyguard;
import fr.utbm.gi.vi51.g3.motion.agent.Medic;
import fr.utbm.gi.vi51.g3.motion.agent.Seller;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Plan;
import fr.utbm.gi.vi51.g3.motion.environment.smellyObjects.Stage;

public class QuadTreeAgentInsertionTest {

	QuadTree tree;

	@Test
	public void testBuild() {

		tree = new QuadTree(8, 8);

		System.out.println("seller");
		Seller seller = new Seller(null);
		tree.insert(new AgentBody(seller.getAddress(), 0.5, 0, 0, 0, 0, 0));
		System.out.println("medic");
		Medic medic = new Medic();
		tree.insert(new AgentBody(medic.getAddress(), 0.5, 0, 0, 0, 0, 0));
		System.out.println("man");
		Attendant man = new Attendant(AttendantGender.MAN);
		tree.insert(new AgentBody(man.getAddress(), 0.5, 0, 0, 0, 0, 0));
		System.out.println("woman");
		Attendant woman = new Attendant(AttendantGender.WOMAN);
		tree.insert(new AgentBody(woman.getAddress(), 0.5, 0, 0, 0, 0, 0));

	}

	@Test
	public void testCull() {
		// testBuild();

		tree = new QuadTree(10000, 10000);
		for (int i = 0; i < 10000; i++) {
			System.out.println("flora " + i);
		}
		// Let's define a frustum
		AABB frustum = new AABB(0, 1, 0, 1);
		// When called, cull function should return a list of 3 objects
		// CM EDIT should be 4 perceived objects to me : flora 1, 3, 4, 5.
		List<Perception> percepts = tree.cull(frustum);

		System.out.println("Total number of perceived objects : "
				+ percepts.size());
		for (Perception p : percepts) {
			System.out.println(p.getPerceivedObject().getClass().toString()
					+ " - " + p.getPerceivedObject().getBox().toString());
		}
	}

}
