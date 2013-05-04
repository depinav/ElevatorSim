package cdm.se350.elevatorsim.elevator;

import java.util.PriorityQueue;

import cdm.se350.elevatorsim.interfaces.ElevatorInt;

public class ElevatorImpl implements ElevatorInt, Runnable {
	
	private String travelDir;
	private boolean isTraveling;
	private static final int DEFAULTFL = 0;
//	private int currentOccup;
//	private int maxOccup;
	private int destination;
	private int doorOpenTime;
	private int speed;
	private PriorityQueue<Integer> destList = new PriorityQueue<Integer>();
	private int maxIdleTime;

	public ElevatorImpl( int _doorOpen, int _speed, int _maxIdle ) {
		
		doorOpenTime = _doorOpen;
		speed = _speed;
		maxIdleTime = _maxIdle;
	}
	
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
