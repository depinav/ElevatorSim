package cdm.se350.elevatorsim;

import cdm.se350.elevatorsim.elevator.ElevatorController;
import cdm.se350.elevatorsim.interfaces.Time;

public class Simulator implements Time {
	
	private int floors;
	private int elevators;
	private int people;
	private long seconds;
	private long time;
	private int scale;
	
	private long timerStart;
	private long timerEnd;
	private long totalTime;
	private long totalSubTime;
	private boolean timerStarted = false;
	private boolean totalTimerPassed = false;
	
	public Simulator(int _floors, int _elevators, int _people, int _scale, long _seconds, long _time) {
		
		setFloors(_floors);
		setElevators(_elevators);
		setPeople(_people);
		setScaled(_scale);
		setSeconds(_seconds);
		setTime(_time);
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
	
	private void setScaled(int theScale) {
		
		scale = theScale;
	}
	
	private void setTime(long theTime) {
		
		time = theTime;
	}
	
	public void run() throws InterruptedException {
		
		Building building = Building.getInstance();
		building.setFloors(floors);
		building.setElevators(elevators);
		building.setScale(scale);
		ElevatorController controller = ElevatorController.getInstance();
		controller.setElevatorList(building.getElevatorList());
		controller.startElevators();
		
		while(!totalTimerPassed) {
			
			if(!timerStarted)
				this.startTimer();
			
			this.countTimer();
			totalSubTime = 0;
			
			while(this.toSec(totalSubTime) <= seconds) {
				
				this.countTimer();
				if(System.currentTimeMillis() != totalSubTime)
					System.out.println("Still counting" + this.toSec(totalSubTime));
			}
			
//			System.out.println("Creating a person");
			//building.addPersons(people);
			//building.startPeople();
			if(totalTime > time)
				totalTimerPassed = true;
		}
		
		System.out.println("Out");
		building.stopPeople();
		controller.stopElevators();
	}

	public long toMilli(long sec) {
		
		return sec * 1000;
	}
	
	public long toSec(long milli) {
		
		return milli / 1000;
	}
	
	public void startTimer() {
		
		timerStart = System.currentTimeMillis();
		timerStarted = true;
	}
	
	public void countTimer() {
		
		timerEnd = System.currentTimeMillis();
		totalTime = this.toSec(timerEnd - timerStart);
		totalSubTime = System.currentTimeMillis() - totalSubTime;
	}
	
	public void endTimer() {
		
		timerEnd = System.currentTimeMillis();
		totalTime = this.toSec(timerEnd - timerStart);
		timerStarted = false;
	}

}
