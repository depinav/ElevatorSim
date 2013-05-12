package cdm.se350.elevatorsim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cdm.se350.elevatorsim.elevator.ElevatorImpl;


public class Building {
	
	private static ArrayList<Floor> floorList = new ArrayList<Floor>();
	private static ArrayList<ElevatorImpl> elevatorList = new ArrayList<ElevatorImpl>();
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private Date date = new Date();
	
	public Building (int floors, int elevators){
		
		for (int i = 0; i < floors; i++){
			  floorList.add(new Floor());
		}
		
		for (int j = 0; j < elevators; j++){
			elevatorList.add(new ElevatorImpl(j, floorList.size()));
		}
		
		System.out.println(dateFormat.format(date) + "\tBuilding created with " + floors + " floors and " + elevators + " elevators");
	}
	
	public ArrayList<ElevatorImpl> getElevatorList() {
		
		return elevatorList;
	}
	
	public void setScale(long _scaled) {
		
		for (int i = 0; i < elevatorList.size(); i++)
			elevatorList.get(i).setScaled(_scaled);
		
		System.out.println(dateFormat.format(date) + "\tScale set to " + _scaled + ":1");
	}
}
