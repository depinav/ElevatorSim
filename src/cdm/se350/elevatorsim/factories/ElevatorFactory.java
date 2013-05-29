package cdm.se350.elevatorsim.factories;

import cdm.se350.elevatorsim.elevator.RegElevator;
import cdm.se350.elevatorsim.interfaces.Elevator;

public class ElevatorFactory {
	
	public Elevator getElevator(String type, int floors, int maxFloors) {
		
		Elevator elevator = null;
		
		if(type.equals("Regular")) {
			elevator = new RegElevator(floors, maxFloors);
		}
		return elevator;
	}

}
