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
	
	Building building = Building.getInstance();

	/**
	 * 
	 * The constructor creates a new ElevatorImpl giving it the elevator number and
	 * the total number of floors in the building.
	 *  
	 * @param 		_elevatorNum 	An int representing the elevator number.
	 * @param 		_maxFloors		An int representing the max amount of floors in the building.
	 * 
	 */
	public RegElevator(int _elevatorNum, int _maxFloors) {
		
		doorOpenTime = 5;
		speed = 1;
		maxIdleTime = 10;
		elevatorNum = _elevatorNum + 1;
		currFloor = DEFAULT;
		maxFloors = _maxFloors;
		this.setState(IDLING);
		
		try{
			if (doorOpenTime < 0) throw new IllegalArgumentException("doorOpenTime cannot be a negative value: " + doorOpenTime);
		}catch (Exception DOTError){
			System.out.println("Error: " + DOTError.getMessage());
		}
		
		try{
			if (speed <= 0) throw new IllegalArgumentException("Speed requires a value greater than 0: " + speed);
		}catch (Exception spdError){
			System.out.println("Error: " + spdError.getMessage());
		}
		
		try{
			if (maxIdleTime < 0) throw new IllegalArgumentException("maxIdleTime cannot be a negative value: " + maxIdleTime);
		}catch (Exception MITError){
			System.out.println("Error: " + MITError.getMessage());
		}
		
		try{
			if (maxFloors < 0) throw new IllegalArgumentException("maxFloors cannot be a negative value: " + maxFloors);
		}catch (Exception mFlrError){
			System.out.println("Error: " + mFlrError.getMessage());
		}
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
	
	/**
	 * 
	 * A method in order to add a destination to the elevators destination list.
	 * @param		newDest			An int representing the floor.
	 * 
	 */
	public void addDest( int newDest ) {
		
		if (state == TODEFAULT)
			destList.clear();
		
		try{
			if(newDest > maxFloors || newDest <0) throw new IllegalArgumentException("newDest requires a value greater than 0 and less than " + maxFloors+ ": " + newDest);
		}catch (Exception destError){
			System.out.println("Error: " + destError.getMessage());
		}
		
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
				//building.getFloorList().get(currFloor).ding();
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
	
	/*public static class ElevatorImplTest extends TestCase {
		private RegElevator elevatorImpl = null;
		private Building building = null;
		
		public ElevatorImplTest (String name) throws Exception {
			super(name);
		}
		
		protected void setUp() throws Exception{
			System.out.println("Setup Building");
			building = new Building (20,5);
			System.out.println("Setup ElevatorImpl");
			elevatorImpl = new RegElevator (3,20);
		}
		
		protected void tearDown(){
			System.out.println("TearDown Building");
			building = null;
			System.out.println("TearDown ElevatorImpl");
			elevatorImpl = null;
		}
		
		public static Test suite (){
			return new TestSuite(ElevatorImplTest.class);
		}
		
		public void testElevatorImplConstructors () throws Exception {
			System.out.println("Test ElevatorImpl Constructors");
			
			try{
				elevatorImpl.doorOpenTime = -5;
				fail("Allowed negative value for door open time");
			}catch (Exception e){
				System.out.println("Okay. Found: " + e.getMessage());
			}
			
			try{
				elevatorImpl.speed = -1;
				fail("Allowed negative value for speed");
			}catch (Exception e2){
				System.out.println("Okay. Found: " + e2.getMessage());
			}
			
			try{
				elevatorImpl.maxIdleTime = -10;
				fail("Allowed negative value for Max Idle Time");
			}catch (Exception e3){
				System.out.println("Okay. Found: " + e3.getMessage());
			}
			
			try{
				elevatorImpl.elevatorNum = -10;
				fail("Allowed negative value for elevatorNum");
			}catch (Exception e4){
				System.out.println("Okay. Found: " + e4.getMessage());
			}

		}
		
		public void testAddDest() throws Exception {
			elevatorImpl.addDest(3);
			elevatorImpl.addDest(4);
			elevatorImpl.addDest(5);
			int[] testList = {3,4,5};
			
			assertTrue("Value: " + elevatorImpl.destList.size(), (elevatorImpl.destList.size()) == (3));
			assertTrue("Value: " + elevatorImpl.destList, elevatorImpl.destList.equals(testList) );
			
		}
		
		public void testNewDest () throws Exception {
			try{
				elevatorImpl.addDest(30);
				fail("Floor does not exist");
			}catch (Exception e){
				System.out.println("Okay. Found: " + e.getMessage());
			}
			
			try{
				elevatorImpl.addDest(-5);
				fail("Allowed negative value for destination");
			}catch (Exception e){
				System.out.println("Okay. Found: " + e.getMessage());
			}
		}
		
	} */
}
