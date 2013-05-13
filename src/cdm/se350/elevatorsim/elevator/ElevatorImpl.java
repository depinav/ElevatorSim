package cdm.se350.elevatorsim.elevator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.PriorityQueue;

import cdm.se350.elevatorsim.interfaces.ElevatorInt;
import cdm.se350.elevatorsim.interfaces.TimeInt;

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

public class ElevatorImpl implements ElevatorInt, Runnable, TimeInt {
	
	private String travelDir;
	private static final int DEFAULT = 1;
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

	/**
	 * 
	 * The constructor creates a new ElevatorImpl giving it the elevator number and
	 * the total number of floors in the building.
	 *  
	 * @param 		_elevatorNum 	An int representing the elevator number.
	 * @param 		_maxFloors		An int representing the max amount of floors in the building.
	 * 
	 */
	public ElevatorImpl(int _elevatorNum, int _maxFloors) {
		
		doorOpenTime = 5;
		speed = 1;
		maxIdleTime = 10;
		elevatorNum = _elevatorNum + 1;
		currFloor = DEFAULT;
		maxFloors = _maxFloors;
		this.setState(IDLING);
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
	
	/**
	 * 
	 * A method in order to add a destination to the elevators destination list.
	 * @param		newDest			An int representing the floor.
	 * 
	 */
	public void addDest( int newDest ) {
		
		if (state == TODEFAULT)
			destList.clear();
		
		synchronized (this) {
			if (newDest != currFloor && newDest > 0 && newDest <= maxFloors) {
				if (destList.isEmpty() && currFloor < newDest) {
					travelDir = "Up";
					destList.add(newDest);
				} else if (destList.isEmpty() && currFloor > newDest) {
					destList = new PriorityQueue<Integer>(1, Collections.reverseOrder());
					travelDir = "Down";
					destList.add(newDest);
				} else if ( (travelDir.equals("Up") && newDest > currFloor) || (travelDir.equals("Down") && newDest < currFloor))
					destList.add(newDest);
				
				System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum + " added floor " + newDest + " to destination list");
			}
		}
		
		synchronized (this) {
			this.notifyAll();
		}
		this.setState(TRAVELING);
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
				System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum + " doors opening...");
				Thread.sleep(this.getScaled(this.toMilli(doorOpenTime)));
				System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum + " doors closing...");
			}
			destList.remove();
		}
	}
	
	/**
	 * 
	 * Prepares the current elevator to be stopped.
	 * 
	 */
	public void stop() {
		
		running = false;
		this.setState(STOPPED);
		synchronized (this) {
			this.notifyAll();
		}
	}
	
	private void setState(int _state) {
		
		state = _state;
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

	public void run() {

		while(running) {
			
			synchronized (this) {
				if (destList.isEmpty()) {
					try {
						if (currFloor != DEFAULT) {
							//System.out.println(this.getScaled(this.toMilli(maxIdleTime)));
							this.wait(this.getScaled(this.toMilli(maxIdleTime)));
						}
						else
							this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			if (!destList.isEmpty()){
				
				if(currFloor == destList.peek()) {
					
					System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum + " reached destination: Floor " + currFloor);
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
				}
				
			} else if (state != STOPPED){
				System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum +  " moving to default floor");
				this.addDest(DEFAULT);
				this.setState(TODEFAULT);
			}
		}
	}
}
