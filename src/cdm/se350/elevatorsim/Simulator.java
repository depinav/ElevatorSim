package cdm.se350.elevatorsim;

import cdm.se350.elevatorsim.elevator.ElevatorController;

public class Simulator {
	
	public void run() throws InterruptedException {
		
		int scale = 2;
		Building myBuilding = new Building(15, 6); // Floors, Elevators
		myBuilding.setScale(scale);
		ElevatorController controller = ElevatorController.getInstance();
		controller.setElevatorList(myBuilding.getElevatorList());
		controller.startElevators();
		controller.sendRequest(1, 11);
		Thread.sleep(6000/scale);
		controller.sendRequest(3, 14);
		Thread.sleep(1000/scale);
		controller.sendRequest(3, 13);
		Thread.sleep(2000/scale);
		controller.sendRequest(3, 15);
		Thread.sleep(6000/scale);
		controller.sendRequest(5, 10);
		Thread.sleep(2000/scale);
		controller.sendRequest(5, 1);
		Thread.sleep(25000/scale);
		controller.sendRequest(5, 5);
		Thread.sleep(1000/scale);
		controller.sendRequest(5, 3);
		Thread.sleep(30000/scale);
		controller.stopElevators();
	}

}
