package cdm.se350.elevatorsim;

import cdm.se350.elevatorsim.elevator.ElevatorController;

public class Simluator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Building myBuilding = new Building(15, 6, 3, 3, 4); // Floors, Elevators, Door Open Time, Elevator Speed, Max Idle Time
		ElevatorController controller = ElevatorController.getInstance(myBuilding.getElevatorList());
		controller.startElevators();
		controller.sendRequest(1, 5);
		controller.sendRequest(1, 7);
		controller.sendRequest(2, 13);
		controller.sendRequest(2, 1);
	}

}
