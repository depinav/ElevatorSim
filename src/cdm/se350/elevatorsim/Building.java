package cdm.se350.elevatorsim;

import java.util.ArrayList;

import cdm.se350.elevatorsim.elevator.ElevatorImpl;


public class Building {
	
	public Building (int floors, int elevators){
		ArrayList<Floor> floorList = new ArrayList<Floor>();
		for (int i = 0; i < floors; i++){
			  floorList.add(new Floor());
			  
		}
		ArrayList<ElevatorImpl> elevatorList = new ArrayList<ElevatorImpl>();
		for (int j = 0; j < elevators; j++){
			elevatorList.add(new ElevatorImpl());
		}
		
	}
}
