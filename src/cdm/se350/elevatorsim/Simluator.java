package cdm.se350.elevatorsim;

import cdm.se350.elevatorsim.elevator.ElevatorController;

public class Simluator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Building myBuilding = new Building(1, 1, 3, 3, 4);
		ElevatorController.getInstance(myBuilding.getElevatorList());
	}

}
