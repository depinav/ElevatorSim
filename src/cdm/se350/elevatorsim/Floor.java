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
//	private static int totalPeople = 1;

	public Floor (){}
	
	public void pressCallBox(int currFl, int destFl) {
		
		String dir;
		ElevatorController controller = ElevatorController.getInstance();
		
		if( currFl > destFl )
			dir = "Up";
		else
			dir = "Down";
		
		controller.request(currFl, dir);
		
		System.out.println(dateFormat.format(new Date()) + "\tFloor " + currFl + " callbox pressed.");
	}
	
	public void ding() {
		
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
		
			peopleList.add(person.getPerson("Regular", initFloor, destFloor, i));
//			totalPeople++;
		}
	}
	
	public void runPeople() {
		
		for(Person person : peopleList) {
			(new Thread(person)).start();
		}
	}
	
	public void endPeople() {
		
		for(Person person : peopleList) {
			
			person.stop();
		}
		
		System.out.println(dateFormat.format(new Date()) + "\tAll people stopped");
	}
}
