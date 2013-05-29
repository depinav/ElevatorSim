package cdm.se350.elevatorsim;

import cdm.se350.elevatorsim.elevator.ElevatorController;
import cdm.se350.elevatorsim.interfaces.Time;

public class Simulator implements Time {
	
	private int floors;
	private int elevators;
	private int people;
	private long seconds;
	
	public Simulator(int _floors, int _elevators, int _people, long _seconds) {
		
		setFloors(_floors);
		setElevators(_elevators);
		setPeople(_people);
		setSeconds(_seconds);
	}
	
	private void setFloors(int numFl) {
		
		floors = numFl;
	}
	
	private void setElevators(int numElevators) {
		
		elevators = numElevators;
	}
	
	private void setPeople(int numPeople) {
		
		people = numPeople;
	}
	
	private void setSeconds(long totalSecs) {
		
		seconds = totalSecs;
	}
	
	public void run() throws InterruptedException {
		
		int scale = 2;
		Building building = Building.getInstance();
		building.setFloors(floors);
		building.setElevators(elevators);
		building.addPersons(people, seconds);
		building.setScale(scale);
		ElevatorController controller = ElevatorController.getInstance();
		controller.setElevatorList(building.getElevatorList());
		controller.startElevators();
		building.startPeople();
		Thread.sleep(5000);
		building.stopPeople();
		controller.stopElevators();
	}

	@Override
	public long toMilli(long sec) {
		
		return sec * 1000;
	}

}
