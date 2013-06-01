package cdm.se350.elevatorsim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cdm.se350.elevatorsim.elevator.ElevatorController;
import cdm.se350.elevatorsim.interfaces.Time;

public class Simulator implements Time {
	
	private int floors;
	private int elevators;
	private int people;
	private long seconds;
	private long time;
	private int scale;
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	private long timerStart;
	private long timerEnd;
	private long totalTime;
	private long subTimeStart;
	private long totalSubTime;
	private boolean timerStarted = false;
	private boolean subTimerStarted = false;
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
		ElevatorController controller = ElevatorController.getInstance();
		
		building.setFloors(floors);
		building.setElevators(elevators);
		building.setScale(scale);
		
		controller.setElevatorList(building.getElevatorList());
		controller.setDefaultFloor(7, 0);
//		controller.startElevators();
		
		while(!totalTimerPassed) {
			
			if(!timerStarted)
				this.startTimer();
			
			if (!subTimerStarted) {
				subTimeStart = System.nanoTime();
				subTimerStarted = true;
			}
			
			this.countTimer();
			
			totalSubTime = System.nanoTime() - subTimeStart;
			if (totalSubTime > this.toNano(seconds)) {
				
				building.addPersons(people);
				building.startPeople();
				subTimerStarted = false;
			}
			
			if(totalTime > time)
				totalTimerPassed = true;
			
		}
		
		Thread.sleep(10000);
		building.stopPeople();
//		controller.stopElevators();
	}

	public long toMilli(long sec) {
		
		return sec * 1000;
	}
	
	public long toNano(long sec) {
		
		return sec * 1000000000;
	}
	
	public long toSec(String kind, long init) {
		
		if(kind.equalsIgnoreCase("milli"))
			return init / 1000;
		else
			return init / 1000000000;
	}
	
	public void startTimer() {
		
		timerStart = System.nanoTime();
		timerStarted = true;
	}
	
	public void countTimer() {
		
		timerEnd = System.nanoTime();
		totalTime = this.toSec("nano" , timerEnd - timerStart);
	}
	
	public void endTimer() {
		
		timerStarted = false;
	}

}
