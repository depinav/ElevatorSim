package cdm.se350.elevatorsim.elevator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class ElevatorController {
	
	private int floorNum;
	private String direction;
	private volatile static ElevatorController controllerInstance;
	private static ArrayList<ElevatorImpl> elevatorList;
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
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
	
	public void sendRequest( int elevator, int dest ) {
		
		elevatorList.get(elevator - 1).addDest(dest);
	}
	
	public void startElevators() {
		
		for (int i = 0; i < elevatorList.size(); i++) {
			
			(new Thread (elevatorList.get(i))).start();
		}
		System.out.println(dateFormat.format(new Date()) + "\tAll Elevators started");
	}
	
	public void stopElevators(){
		
		for (int i = 0; i < elevatorList.size(); i++) {
			elevatorList.get(i).stop();
		}
		System.out.println(dateFormat.format(new Date()) + "\tAll Elevators stopped");
	}
	
	public ElevatorImpl getElevator(int i) {
		
		return elevatorList.get(i - 1);
	}
}
