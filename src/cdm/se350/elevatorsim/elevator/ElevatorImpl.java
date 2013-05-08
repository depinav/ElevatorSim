package cdm.se350.elevatorsim.elevator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PriorityQueue;

import cdm.se350.elevatorsim.interfaces.ElevatorInt;

public class ElevatorImpl implements ElevatorInt, Runnable {
	
	private String travelDir;
	private boolean isTraveling;
	private static final int DEFAULT = 1;
//	private int currentOccup;
//	private int maxOccup;
	private int destination;
	private double doorOpenTime;
	private double speed;
	private PriorityQueue<Integer> destList = new PriorityQueue<Integer>();
	private double maxIdleTime;
	private int currFloor;
	private int elevatorNum;
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private Date date = new Date();
	private int state;
	private static final int TODEFAULT = 0;
	private static final int TRAVELING = 1;

	public ElevatorImpl( double _doorOpen, double _speed, double _maxIdle, int _elevatorNum ) {
		
		doorOpenTime = _doorOpen;
		speed = _speed;
		maxIdleTime = _maxIdle;
		elevatorNum = _elevatorNum + 1;
		currFloor = DEFAULT;
	}
	
	public boolean isIdle() {
		return isTraveling;
	}

	public void toDefault() {
		
		travelDir = "Down";
		destList.add(DEFAULT);
		state = TODEFAULT;
	}
	
	public PriorityQueue<Integer> getDestList() {
		
		return destList;
	}
	
	public void addDest( int newDest ) {
		
		if (destList.isEmpty() && currFloor < newDest) {
			travelDir = "Up";
			destList.add(newDest);
		} else if (destList.isEmpty() && currFloor > newDest) {
			travelDir = "Down";
			destList.add(newDest);
		} else if ( (travelDir.equals("Up") && newDest > currFloor) || (travelDir.equals("Down") && newDest < currFloor))
			destList.add(newDest);
		
		state = TRAVELING;
	}

	public void run() {
		
		while(true) {
			
			long startIdleTime = System.nanoTime();
			while(destList.isEmpty() && currFloor != DEFAULT) {

				long endIdleTime = System.nanoTime();
				if ((double)((endIdleTime - startIdleTime) / 1000000000.0) >= maxIdleTime) {
					this.toDefault();
					System.out.println(dateFormat.format(date) + "\tElevator " + elevatorNum +  " moving to default floor");
				}
			}
			
			while (!destList.isEmpty()) {
				
				while(destList.peek() != currFloor) {
					
					boolean betweenFloors = true;
					long startSpeed = System.nanoTime();
					while (betweenFloors) {
						
						long endSpeed = System.nanoTime();
						if((double)((endSpeed - startSpeed) / 1000000000.0) >= speed) {
							System.out.println(dateFormat.format(date) + "\tElevator " + elevatorNum + " passing floor " + currFloor);
							betweenFloors = false;
							if (travelDir.equals("Up"))
								currFloor++;
							if (travelDir.equals("Down"))
								currFloor--;
						}
					}
				}
				
				if (destList.peek() == currFloor) {
					System.out.println(dateFormat.format(date) + "\tElevator " + elevatorNum + " reached destination floor " + currFloor);
					if (state == TODEFAULT){
					} else {
						
						System.out.println(dateFormat.format(date) + "\tElevator " + elevatorNum + " doors Opening...");
						boolean doorsOpened = true;
						long doorOpenStartTime = System.nanoTime();
						while (doorsOpened) {
							long doorOpenEndTime = System.nanoTime();
							if ((double)((doorOpenEndTime - doorOpenStartTime) / 1000000000.0) >= doorOpenTime) {
								System.out.println(dateFormat.format(date) + "\tElevator " + elevatorNum + " doors Closing...");
								doorsOpened = false;
							}
						}
					}
					destList.remove();
				} 
			}
		}
	}
}
