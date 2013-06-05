package cdm.se350.elevatorsim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import cdm.se350.elevatorsim.elevator.ElevatorController;
import cdm.se350.elevatorsim.interfaces.Person;
import cdm.se350.elevatorsim.interfaces.Time;

public class Simulator implements Time {
	
	private int floors;
	private int elevators;
	private int people;
	private long seconds;
	private long time;
	private int alg;
	private int doors;
	private int speed;
	private int idle;
	private int occup;
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
	
	private HashMap<Person, Long> personWaitTime = new HashMap<Person, Long>();
	
	public Simulator(int _floors, int _elevators, int _people, int _scale, long _seconds, long _time, int _alg, int _doors, int _speed, int _idle, int _occup) {
		
		setFloors(_floors);
		setElevators(_elevators);
		setPeople(_people);
		setScaled(_scale);
		setSeconds(_seconds);
		setTime(_time);
		setAlg(_alg);
		setDoors(_doors);
		setSpeed(_speed);
		setIdle(_idle);
		setOccup(_occup);
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
	
	private void setAlg(int theAlg){
		
		alg = theAlg;
	}
	
	private void setDoors(int theDoors) {
		
		doors = theDoors;
	}
	
	private void setSpeed(int theSpeed) {
		
		speed = theSpeed;
	}
	
	private void setIdle(int theIdle) {
		
		idle = theIdle;
	}
	
	private void setOccup(int theOccup) {
		
		occup = theOccup;
	}
	
	public void run() throws InterruptedException {
		
		Building building = Building.getInstance();
		ElevatorController controller = ElevatorController.getInstance();
		
		building.setFloors(floors);
		building.setElevators(elevators, doors, speed, idle, occup);
		building.setScale(scale);
		
		controller.setElevatorList(building.getElevatorList());
		controller.setDefaultFloor(7, 0);
		controller.setAlg(alg);
		controller.startElevators();
		
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
		Thread.sleep(this.toScaled(60000));
		building.stopPeople();
		controller.stopElevators();
		
		for(int i = 0; i < building.getFloor().getPersonList().size(); i++) {
			personWaitTime.put(building.getFloor().getPersonList().get(i), building.getFloor().getPersonList().get(i).getWaitTime());
		}
	}

	public long toMilli(long sec) {
		
		return sec * 1000;
	}
	
	public long toScaled(long unscaled) {
		
		return unscaled/scale;
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
