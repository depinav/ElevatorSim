package cdm.se350.elevatorsim.elevator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.PriorityQueue;

import cdm.se350.elevatorsim.interfaces.ElevatorInt;
import cdm.se350.elevatorsim.interfaces.TimeInt;

public class ElevatorImpl implements ElevatorInt, Runnable, TimeInt {
	
	private String travelDir;
	private static final int DEFAULT = 1;
	private int currentOccup;
	private int maxOccup;
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

	public ElevatorImpl(int _elevatorNum) {
		
		doorOpenTime = 5;
		speed = 1;
		maxIdleTime = 10;
		elevatorNum = _elevatorNum + 1;
		currFloor = DEFAULT;
		this.setState(IDLING);
	}
	
	public PriorityQueue<Integer> getDestList() {
		
		return destList;
	}
	
	public void addDest( int newDest ) {
		
		if (state == TODEFAULT)
			destList.clear();
		
		synchronized (this) {
			if (currFloor != newDest) {
				if (destList.isEmpty() && currFloor < newDest) {
					travelDir = "Up";
					destList.add(newDest);
				} else if (destList.isEmpty() && currFloor > newDest) {
					destList = new PriorityQueue<Integer>(1, Collections.reverseOrder());
					travelDir = "Down";
					destList.add(newDest);
				} else if ( (travelDir.equals("Up") && newDest > currFloor) || (travelDir.equals("Down") && newDest < currFloor))
					destList.add(newDest);
			}
		}
		
		synchronized (this) {
			this.notifyAll();
		}
		this.setState(TRAVELING);
	}
	
	public void arrived() throws InterruptedException {
		
		synchronized (this) {
			if (state == TODEFAULT) {
				this.setState(IDLING);
			} else {
				System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum + " doors opening...");
				Thread.sleep(this.toMilli(this.getScaled(doorOpenTime)));
				System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum + " doors closing...");
			}
			destList.remove();
		}
	}
	
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

	public long toMilli(long sec) {
		
		return sec * 1000;
	}
	
	public void setScaled(long _scaled) {
		
	}
	
	public long getScaled(long unscaled) {
		
		return (long)((int)unscaled / (int)scaled);
	}

	public void run() {

		while(running) {
			
			synchronized (this) {
				if (destList.isEmpty()) {
					try {
						if (currFloor != DEFAULT)
							this.wait(this.toMilli(this.getScaled(maxIdleTime)));
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
						Thread.sleep(this.toMilli(this.getScaled(speed)));
					} catch (InterruptedException e) {
						System.out.println("Error with speed of elevator " + elevatorNum);
						e.printStackTrace();
					}
					System.out.println(dateFormat.format(new Date()) + "\tElevator " + elevatorNum + " passing floor " + currFloor);
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
