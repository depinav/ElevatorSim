package cdm.se350.elevatorsim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import cdm.se350.elevatorsim.elevator.ElevatorController;
import cdm.se350.elevatorsim.factories.PersonFactory;
import cdm.se350.elevatorsim.interfaces.Person;

public class Floor {
	
	private ArrayList<Person> peopleList = new ArrayList<Person>();
	private Building building = Building.getInstance();
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	public static int totalPeople = 1;
	private static int iPeople = 0;

	public Floor (){}
	
	public void pressCallBox(int currFl, int destFl) {
		
		String dir;
		ElevatorController controller = ElevatorController.getInstance();
		
		if( currFl > destFl )
			dir = "Up";
		else
			dir = "Down";
		
		System.out.println(dateFormat.format(new Date()) + "\tFloor " + currFl + " callbox pressed.");
		controller.request(currFl, dir);
	}
	
	public void ding(int floorNum, int elevator) {
		
		for(int i = 0; i < peopleList.size(); i++) {
			
			if(peopleList.get(i).getCurrent() == floorNum)
				peopleList.get(i).elevatorArrived(floorNum, elevator);
		}
	}
	
	public void enterElevator(int elevatorNum) {
		
		building.getElevatorList().get(elevatorNum).addPassenger();
	}
	
	public void createPeople(int people) {
		
		for(int i = 0; i < people; i++) {
			
			Random ran = new Random();
			PersonFactory person = new PersonFactory();
			int destFloor;
			int initFloor = ran.nextInt(building.getFloorList() - 1) + 1;
			do {
				
				destFloor = ran.nextInt(building.getFloorList() - 1) + 1;
			} while(destFloor == initFloor);
		
			peopleList.add(person.getPerson("Regular", initFloor, destFloor));
			totalPeople++;
		}		
	}
	
	public void runPeople() {
		
		for(; iPeople < peopleList.size(); iPeople++) {
			
			new Thread(peopleList.get(iPeople)).start();
		}
	}
	
	public void endPeople() {
		
		for(Person person : peopleList) {
			
			person.stop();
		}
		
		System.out.println(dateFormat.format(new Date()) + "\tAll people stopped");
	}
}
