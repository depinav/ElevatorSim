package cdm.se350.elevatorsim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cdm.se350.elevatorsim.interfaces.Person;

public class People implements Person {

	private int currentFloor;
	private int destFloor;
	private int elevatorFloor;
	private int elevator;
	private Building building = Building.getInstance();
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private int personNo;
	private boolean active = true;
	
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
		
		floor = elevatorFloor;
		elevator = _elevator;
		synchronized(this) {
			this.notifyAll();
		}
	}
	
	public void enterElevator(int elevator) {
		
		building.getFloor().enterElevator(elevator);
	}
	
	public void exitElevator() {
		
	}
	
	public void stop() {
		
		active = false;
		synchronized (this) {
			this.notifyAll();
		}
	}
	
	public void run() {
		
		System.out.println(dateFormat.format(new Date()) + "\tPerson " + personNo + " on " + this.getCurrent() + " Floor. Destination: " + this.getDest());
		building.getFloor().pressCallBox(getCurrent(), getDest());
		while(active) {
			
			synchronized(this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (elevatorFloor == currentFloor) {
				this.enterElevator(elevator);
			}
		}
	}
}
