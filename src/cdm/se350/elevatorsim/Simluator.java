package cdm.se350.elevatorsim;

import cdm.se350.elevatorsim.elevator.ElevatorController;

public class Simluator {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		Building myBuilding = new Building(15, 6); // Floors, Elevators, Door Open Time, Elevator Speed, Max Idle Time
		ElevatorController controller = ElevatorController.getInstance(myBuilding.getElevatorList());
		controller.startElevators();
		controller.sendRequest(1, 11);
		Thread.sleep(6000);
		controller.sendRequest(3, 14);
		Thread.sleep(1000);
		controller.sendRequest(3, 13);
		Thread.sleep(2000);
		controller.sendRequest(3, 15);
		Thread.sleep(15000);
		controller.sendRequest(5, 10);
		Thread.sleep(2000);
		controller.sendRequest(5, 1);
		Thread.sleep(8000);
		controller.sendRequest(5, 5);
		Thread.sleep(1000);
		controller.sendRequest(5, 3);
	}
}
