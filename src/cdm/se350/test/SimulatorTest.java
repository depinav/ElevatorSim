package cdm.se350.test;

import org.junit.Test;

import cdm.se350.elevatorsim.Simulator;

public class SimulatorTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void testSimulatorConstructor() {
		Simulator s = new Simulator(2, 3, 1, -1, 1, 0, 1, 2, 1, 1, 1);
	}
}
