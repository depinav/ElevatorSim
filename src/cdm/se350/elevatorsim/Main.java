package cdm.se350.elevatorsim;

import cdm.se350.elevatorsim.elevator.ElevatorController;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Simulator simulate = new Simulator();
		simulate.run();
	}
}
