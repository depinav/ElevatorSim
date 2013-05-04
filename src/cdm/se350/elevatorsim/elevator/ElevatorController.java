package cdm.se350.elevatorsim.elevator;

import java.util.ArrayList;

public final class ElevatorController {
	
	private int floorNum;
	private String direction;
	private volatile static ElevatorController controllerInstance;
	private static ArrayList<ElevatorImpl> elevatorList;
	
	private ElevatorController(){};
	
	public String getDirection() {
		
		return direction;
	}
	
	public int getFloorNum() {
		
		return floorNum;
	}
	
	public void getFloorList() {
		
	}
	
	public static ElevatorController getInstance(ArrayList<ElevatorImpl> _elevatorList) {
		
		if (controllerInstance == null) {
			synchronized (ElevatorController.class) {
				if (controllerInstance == null) {
					controllerInstance = new ElevatorController();
					elevatorList = _elevatorList;
				}
			}
		}
		return controllerInstance;
	}
	
	public void sendRequest( int dest, int elevator ) {
		
		elevatorList.get(elevator).addDest(dest);
	}
}
