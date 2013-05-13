package cdm.se350.elevatorsim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cdm.se350.elevatorsim.elevator.ElevatorImpl;

/**
 * 
 * A building class that holds a custom number of floors and elevators
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public class Building {
	
	private static ArrayList<Floor> floorList = new ArrayList<Floor>();
	private static ArrayList<ElevatorImpl> elevatorList = new ArrayList<ElevatorImpl>();
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private Date date = new Date();
	
	/**
	 * Creates a new building with a specified number of floors and elevators.
	 * 
	 * @param 		floors			The int representing the number of floors the building will hold.
	 * @param 		elevators		The int representing the number of elevators in the building.
	 * 
	 */
	public Building (int floors, int elevators){
		
		for (int i = 0; i < floors; i++){
			  floorList.add(new Floor());
		}
		
		for (int j = 0; j < elevators; j++){
			elevatorList.add(new ElevatorImpl(j, floorList.size()));
		}
		
		System.out.println(dateFormat.format(date) + "\tBuilding created with " + floors + " floors and " + elevators + " elevators");
	}
	
	/**
	 * Returns the collection of the elevator list of the building.
	 * @return		The collection holding the list of elevators.
	 */
	public ArrayList<ElevatorImpl> getElevatorList() {
		
		return elevatorList;
	}
	
	/**
	 * Sets the scale that the system will use for time.
	 * @param 		_scaled		The long representing the scaled time.
	 */
	public void setScale(long _scaled) {
		
		for (int i = 0; i < elevatorList.size(); i++)
			elevatorList.get(i).setScaled(_scaled);
		
		System.out.println(dateFormat.format(date) + "\tScale set to " + _scaled + ":1");
	}
}
