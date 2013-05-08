package cdm.se350.elevatorsim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cdm.se350.elevatorsim.elevator.ElevatorController;
import cdm.se350.elevatorsim.elevator.ElevatorImpl;


public class Building {
	
	private static ArrayList<Floor> floorList = new ArrayList<Floor>();
	private static ArrayList<ElevatorImpl> elevatorList = new ArrayList<ElevatorImpl>();
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private Date date = new Date();
	
	public Building (int floors, int elevators, int doorsOpen, int elevSpeed, int idleTime){
		
		for (int i = 0; i < floors; i++){
			  floorList.add(new Floor());
		}
		
		for (int j = 0; j < elevators; j++){
			elevatorList.add(new ElevatorImpl(doorsOpen, elevSpeed, idleTime, j));
		}
		
		System.out.println(dateFormat.format(date) + "\tBuilding created with " + floors + " floors and " + elevators + " elevators");
	}
	
	public ArrayList<ElevatorImpl> getElevatorList() {
		
		return elevatorList;
	}
}
