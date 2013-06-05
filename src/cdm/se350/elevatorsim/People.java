package cdm.se350.elevatorsim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cdm.se350.elevatorsim.interfaces.Person;
import cdm.se350.elevatorsim.interfaces.Time;

public class People implements Person, Time {

	private int currentFloor;
	private int destFloor;
	private int elevatorFloor;
	private int elevator;
	private int calledElevator;
	private Building building = Building.getInstance();
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private int personNo;
	private boolean active = true;
	private boolean inElevator = false;
	
	private long floorWaitTime;
	private long inElevatorWaitTime;
	private long timerStart;
	private long timerEnd;
	private long totalTime;
	private boolean timerStarted = false;
	
	public People(int currFl, int destFl) {
		
		try {
			if (currFl <= 0 || currFl > building.getFloorList()) 
				throw new IllegalArgumentException("Current floor cannot have a value less than 0 or greater the total floor number: " + building.getFloorList() + " :" + currFl);
			else
				this.setCurr(currFl);
		} catch (Exception flrError) {
			System.out.println("Error: " + flrError.getMessage());
		}
		
		try {
			if (destFl <= 0 || destFl > building.getFloorList()) 
				throw new IllegalArgumentException("Destination floor cannot have a value less than 0 or greater the total floor number: " + building.getFloorList() + " :" + currFl);
			else
				this.setDest(destFl);
		} catch (Exception flrError) {
			System.out.println("Error: " + flrError.getMessage());
		}
		
		this.setPersonNo(Floor.totalPeople);
	}
	
	private void setCurr(int current) {
		
		currentFloor = current;
	}
	
	public int getCurrent() {
		
		return currentFloor;
	}
	
	private void setDest(int dest) {
		
		destFloor = dest;
	}
	
	public int getDest() {
		
		return destFloor;
	}
	
	private void setPersonNo(int _personNo) {

		personNo = _personNo;
	}
	
	public int getNumber() {
		
		return personNo;
	}
	
	public void elevatorArrived(int floor, int _elevator) {
		
		elevatorFloor = floor;
		
		if(!inElevator) {
			elevator = _elevator;
		}
		else
			calledElevator = _elevator;
		
		synchronized(this) {
			this.notifyAll();
		}
	}
	
	public void enterElevator() {
		
		System.out.println(dateFormat.format(new Date()) + "\tPerson " + personNo + " entered Elevator " + this.elevator);
		building.getFloor().enterElevator(this.elevator - 1);
		inElevator = true;
		building.getElevatorList().get(this.elevator - 1).addDest(destFloor);
	}
	
	public void exitElevator() {
		
		active = false;
		inElevator = false;
		System.out.println(dateFormat.format(new Date()) + "\tPerson " + personNo + " exited elevator " + elevator);
		building.getFloor().exitElevator(elevator - 1);
	}
	
	public boolean inElevator() {
		
		return inElevator;
	}
	
	public void stop() {
		
		active = false;
		inElevator = false;
		synchronized (this) {
			this.notifyAll();
		}
	}
	
	public void run() {
		
		System.out.println(dateFormat.format(new Date()) + "\tPerson " + personNo + " on Floor " + this.getCurrent() + ". Destination: " + this.getDest());
		building.getFloor().pressCallBox(getCurrent(), getDest());
		while(active) {
			
			if(active) {
				synchronized(this) {
					try {
						this.startTimer();
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			this.endTimer();
			floorWaitTime = totalTime;
			
			if(active) {
				this.enterElevator();
			}
			
			while(inElevator) {
				
				if (active) {
					synchronized(this) {
						try {
							this.startTimer();
							this.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
				if(elevator == calledElevator && elevatorFloor == destFloor)
					this.exitElevator();
				this.endTimer();
				inElevatorWaitTime = totalTime;
			}
		}
		
		System.out.println(dateFormat.format(new Date()) + "\tPerson " + personNo + " total floor wait time " + floorWaitTime + " seconds.");
		System.out.println(dateFormat.format(new Date()) + "\tPerson " + personNo + " total elevator travel time " + inElevatorWaitTime + " seconds.");
		
		
	}

	public long toMilli(long sec) {
		
		return sec / 1000000;
	}
	
	public long toSec(String kind, long init) {
		
		if("milli".equalsIgnoreCase(kind))
			return init / 1000;
		if("nano".equalsIgnoreCase(kind))
			return init / 1000000000;
		else
			return 0;
	}

	public long toNano(long sec) {
		
		return sec * 1000000000;
	}

	public void startTimer() {
		
		timerStart = System.nanoTime();
	}
	
	public void countTimer() {
		
		timerEnd = System.nanoTime();
	}
	
	public void endTimer() {
		
		timerEnd = System.nanoTime();
		totalTime = this.toSec("nano", timerEnd - timerStart);
	}
	
	public long getWaitTime() {

		return floorWaitTime;
	}
	
	public long getTravelTime() {
		
		return inElevatorWaitTime;
	}
}
