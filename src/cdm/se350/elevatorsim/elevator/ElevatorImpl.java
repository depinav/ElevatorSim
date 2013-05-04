package cdm.se350.elevatorsim.elevator;

import java.util.PriorityQueue;

import cdm.se350.elevatorsim.interfaces.ElevatorInt;

public class ElevatorImpl implements ElevatorInt, Runnable {
	
	private String travelDir;
	private boolean isTraveling;
	private static final int DEFAULTFL = 0;
	private int currentOccup;
	private int maxOccup;
	private int destination;
	private int doorOpenTime;
	private int speed;
	private PriorityQueue<Integer> destList = new PriorityQueue<Integer>();
	private int idleTime;
	private int maxIdleTime;

	public boolean isIdle() {
		return isTraveling;
	}

	public void toDefault() {
		
//		Go to default floor after certain amount time
		destList.add(DEFAULTFL);
	}

	public void run() {
		
	}

}
