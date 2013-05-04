package cdm.se350.elevatorsim.elevator;

import java.util.PriorityQueue;

import cdm.se350.elevatorsim.interfaces.ElevatorInt;

public class ElevatorImpl implements ElevatorInt, Runnable {
	
	private String travelDir;
	private boolean isTraveling;
	private static final int DEFAULT = 0;
//	private int currentOccup;
//	private int maxOccup;
	private int destination;
	private int doorOpenTime;
	private int speed;
	private PriorityQueue<Integer> destList = new PriorityQueue<Integer>();
	private int maxIdleTime;
	private int currFloor;

	public ElevatorImpl( int _doorOpen, int _speed, int _maxIdle ) {
		
		doorOpenTime = _doorOpen;
		speed = _speed;
		maxIdleTime = _maxIdle;
	}
	
	public boolean isIdle() {
		return isTraveling;
	}

	public void toDefault() {
		
		destList.add(DEFAULT);
	}
	
	public PriorityQueue<Integer> getDestList() {
		
		return destList;
	}
	
	public void addDest( int newDest ) {
		
		if ( (travelDir.equals("Up") && newDest > currFloor) || (travelDir.equals("Down") && newDest < currFloor))
			destList.add(newDest);
	}

	public void run() {
		
	}
}
