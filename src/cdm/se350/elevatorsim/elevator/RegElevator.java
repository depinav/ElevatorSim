package cdm.se350.elevatorsim.elevator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.PriorityQueue;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import cdm.se350.elevatorsim.Building;
import cdm.se350.elevatorsim.interfaces.Elevator;
import cdm.se350.elevatorsim.interfaces.Time;

/**
 * 
 * The ElevatorImpl class is the implementation of the ElevatorInt interface.
 * It generates an elevator that runs in it's own thread, therefore implements
 * runnable interface.
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public class RegElevator implements Elevator, Runnable, Time {
	
	private String travelDir;
	private String requestDir;
	private int DEFAULT = 1;
	private int currentOccup;
	private int maxOccup;
	private int maxFloors;
	private long doorOpenTime;
	private long speed;
	private PriorityQueue<Integer> destList = new PriorityQueue<Integer>();
	private long maxIdleTime;
	private int currFloor;
	private int elevatorNum;
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private boolean running = true;
	private long scaled = 1;
	private int state;
	private static final int TODEFAULT = 1;
	private static final int TRAVELING = 2;
	private static final int IDLING = 3;
	private static final int STOPPED = 4;
	
	private long timerStart;
	private long timerEnd;
	private long totalTime;
	private boolean timerStarted = false;
	
	Building building = Building.getInstance();
	ElevatorController controller = ElevatorController.getInstance();

	/**
	 * 
	 * The constructor creates a new ElevatorImpl giving it the elevator number and
	 * the total number of floors in the building.
	 *  
	 * @param 		_elevatorNum 	An int representing the elevator number.
	 * @param 		_maxFloors		An int representing the max amount of floors in the building.
	 * 
	 */
	public RegElevator(int _elevatorNum, int _maxFloors, int _doorOpenTime, int _speed, int _maxIdleTime, int _maxOccup) {

		elevatorNum = _elevatorNum + 1;
		currFloor = DEFAULT;
		this.setState(IDLING);
		
			if (_doorOpenTime < 0) throw new IllegalArgumentException("doorOpenTime cannot be a negative value: " + doorOpenTime);
			else
				this.setdoorOpenTime(_doorOpenTime);
		
			if (_speed <= 0) throw new IllegalArgumentException("Speed requires a value greater than 0: " + speed);
			else
				this.setSpeed(_speed);
			
			if (maxIdleTime < 0) throw new IllegalArgumentException("maxIdleTime cannot be a negative value: " + maxIdleTime);
			else
				this.setMaxIdleTime(_maxIdleTime);

			if (_maxFloors < 0) throw new IllegalArgumentException("maxFloors cannot be a negative value: " + maxFloors);
			else
				this.setMaxFloors(_maxFloors);

			if (_maxOccup < 0) throw new IllegalArgumentException("maxFloors cannot be a negative value: " + maxFloors);
			else
				this.setMaxOccup(_maxOccup);
	}
	
	private void setdoorOpenTime(int time) {
		
		doorOpenTime = time;
	}
	
	private void setMaxFloors(int max) {
		
		maxFloors = max;
	}
	
	private void setMaxIdleTime(int time) {
		
		maxIdleTime = time;
	}
	
	private void setMaxOccup(int max) {
		
		maxOccup = max;
	}
	
	private void setSpeed(int theSpeed) {
		
		speed = theSpeed;
	}
	
	/**
	 * 
	 * A method to return the destination list of the current ElevatorImpl
	 * @return		A collection of the destination list
	 * 
	 */
	public PriorityQueue<Integer> getDestList() {
		
		return destList;
	}
	
	public int getCurrFloor(){
		return currFloor;
	}
	
	public String getTravelDir(){
		return travelDir;
	}
	
	public String getRequestDir(){
		return requestDir;
	}
	
	/**
	 * 
	 * A method in order to add a destination to the elevators destination list.
	 * @param		newDest			An int representing the floor.
	 * 
	 */
	public void addDest( int newDest ) {
		
		if (state == TODEFAULT)
			destList.clear();
		
			if(newDest > maxFloors || newDest < 0) throw new IllegalArgumentException("newDest requires a value greater than 0 and less than " + maxFloors+ ": " + newDest);
		
		synchronized (this) {
			if (newDest != currFloor && newDest > 0 && newDest <= maxFloors) {
				if (destList.isEmpty() && currFloor < newDest) {
					destList = new PriorityQueue<Integer>();
					travelDir = "Up";
					destList.add(newDest);
				} else if (destList.isEmpty() && currFloor > newDest) {
					destList = new PriorityQueue<Integer>(1, Collections.reverseOrder());
					travelDir = "Down";
					destList.add(newDest);
				} else if ( ((travelDir.equals("Up") && newDest > currFloor) || (travelDir.equals("Down") && newDest < currFloor)) && !destList.contains(newDest))
					destList.add(newDest);
				
				System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum + " added floor " + newDest + " to destination list");
			} else if (newDest == currFloor && destList.isEmpty())
				destList.add(newDest);
		}
		
		synchronized (this) {
			this.setState(TRAVELING);
			this.notifyAll();
		}
	}
	
	public void requestElevator(String dir, int floor) {
		
		requestDir = dir;
		this.addDest(floor);
	}
	
	/**
	 * 
	 * Initiates certain actions to take place when an elevator arrives to a specified floor
	 * from it's destination list.
	 * 
	 */
	public void arrived() throws InterruptedException {
		
		synchronized (this) {
			if (state == TODEFAULT) {
				this.setState(IDLING);
			} else {
				building.getFloor().ding(currFloor, elevatorNum);
				
				if (destList.isEmpty()) {
					travelDir = null;
					requestDir = null;
				}
				
				this.notifyAll();
				System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum + " doors opening...");
				Thread.sleep(this.getScaled(this.toMilli(doorOpenTime)));
				System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum + " doors closing...");
			}
			destList.remove();
		}
	}
	
	/**
	 * 
	 * Increments the amount of passengers inside of the elevator by one.
	 */
	public void addPassenger() {
		
		synchronized(this) {
			currentOccup++;
		}
		
		System.out.println("Elevator " + elevatorNum + " total passenger count: " + currentOccup);
	}
	
	/**
	 * Decrements the number of passengers inside the elevator by one.
	 */
	public void removePassenger() {
		
		synchronized(this) {
			if(currentOccup > 0)
				currentOccup--;
		}
		
		System.out.println("Elevator " + elevatorNum + " total passenger count: " + currentOccup);
	}
	
	/**
	 * 
	 * Used to check if the elevators occupancy is full.
	 * @return	true if the current occupancy exceeds the maximum occupancy.
	 * 
	 */
	public boolean isFull() {
		
		if (currentOccup < maxOccup)
			return false;
		else
			return true;
	}
	
	/**
	 * 
	 * Prepares the current elevator to be stopped.
	 * 
	 */
	public void stop() {
		
		running = false;
//		System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum + " total passenger count: " + currentOccup);
		this.setState(STOPPED);
		synchronized (this) {
			this.notifyAll();
		}
	}
	
	private void setState(int _state) {
		
		state = _state;
	}
	
	/**
	 * Sets the default floor, or where the elevator will begin and default to after idling.
	 * @param	An int that represents a floor number.
	 */
	public void setDefault(int floorNum) {
		
		DEFAULT = floorNum;
		currFloor = DEFAULT;
	}

	/**
	 * 
	 * Converts seconds value into milliseconds.
	 * @return		A long representing the seconds.
	 */
	public long toMilli(long sec) {
		
		return sec * 1000;
	}
	
	/**
	 * 
	 * Converts nanoseconds or milliseconds to seconds
	 * @param		A string representing the kind of value you are converting from (ie: nano, milli).
	 * @param		A long of the time that you want converted.
	 */
	public long toSec(String kind, long init) {
		
		if(kind.equalsIgnoreCase("milli"))
			return init / 1000;
		else
			return init / 1000000000;
	}
	
	/**
	 * 
	 * Used to set the scale for the speed of the running time
	 * ie: 4:1
	 * 
	 * @param 		_scaled		A long representing the scaled amount.
	 * 
	 */
	public void setScaled(long _scaled) {
		scaled = _scaled;
	}
	
	/**
	 * 
	 * Returns the the value of the time scaled
	 * @param 		unscaled	A long representing the unscaled value.
	 * @return		The long representing the unscaled value divided by the scale amount.
	 * 
	 */
	public long getScaled(long unscaled) {
		
		return unscaled / scaled;
	}
	
	/**
	 * Begins a timer and sets a flag that the timer has started.
	 */
	public void startTimer() {
		
		timerStart = System.currentTimeMillis();
		timerStarted = true;
	}
	
	/**
	 * Takes in the current time, used after startTimer() in order to keep a running count. 
	 */
	public void countTimer() {
		
		timerEnd = System.currentTimeMillis();
	}
	
	/**
	 * Ends the timer and sets timer started flag to false.
	 * Subtracts the start time with the end time and saves the total as seconds in a new attribute.
	 * 
	 */
	public void endTimer() {
		
		timerEnd = System.currentTimeMillis();
		totalTime = this.toSec("nano", timerEnd - timerStart);
		timerStarted = false;
	}

	public void run() {
		
		this.setState(IDLING);
		
		while(running) {
			
			synchronized (this) {
				if (destList.isEmpty()) {
					if( !controller.getPendingList().isEmpty() ) {
						controller.pending();
					}
					
					try {
						if (currFloor != DEFAULT) {
							this.wait(this.getScaled(this.toMilli(maxIdleTime)));
						}
						else {
							this.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			if (!destList.isEmpty()){
				
				if(!timerStarted) {
					this.startTimer();
				}
				
				if(currFloor == destList.peek()) {
					
					System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum + " reached destination: Floor " + currFloor);
					this.endTimer();
					try {
						this.arrived();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					
					try {
						Thread.sleep(this.getScaled(this.toMilli(speed)));
					} catch (InterruptedException e) {
						System.out.println("Error with speed of elevator " + elevatorNum);
						e.printStackTrace();
					}
					System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum + " passing floor " + currFloor + " Full destination list " + destList);
					if (travelDir.equals("Up"))
						currFloor++;
					if (travelDir.equals("Down"))
						currFloor--;
					
					//movingTimeEnd = System.currentTimeMillis();
				}
				
			} else if (state != STOPPED){
				System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum +  " moving to default floor");
				this.addDest(DEFAULT);
				this.setState(TODEFAULT);
			}
		}
	}

	/**
	 * Converts seconds to nanseconds
	 * @param		A long representing the seconds to convert.
	 */
	public long toNano(long sec) {
		
		return sec * 1000000000;
	}
}
