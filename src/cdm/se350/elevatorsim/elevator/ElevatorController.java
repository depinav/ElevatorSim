package cdm.se350.elevatorsim.elevator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import cdm.se350.elevatorsim.factories.ElevatorFactory;
import cdm.se350.elevatorsim.interfaces.Elevator;
import cdm.se350.elevatorsim.interfaces.RequestResponse;
import cdm.se350.elevatorsim.algorithms.*;
import cdm.se350.elevatorsim.factories.AlgorithmPendingFactory;
import cdm.se350.elevatorsim.factories.AlgorithmRequestFactory;
/**
 * 
 * The ElevatorController class is used to control the requests sent to a collection of elevators
 * including, sendRequest, startElevators and stopElevators.
 * This is a Singleton class
 * <pre>
 * ElevatorController controller = ElevatorController.getInstance();
 * </pre>
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public final class ElevatorController {
	
	private int floorNum;
	private String direction;
	private volatile static ElevatorController controllerInstance;
	private static ArrayList<Elevator> elevatorList;
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	ResponseAlgorithmSelection requestAlg = null;
	//PendingAlgorithmSelection pendingAlg = null;
	
	//private ArrayList<Integer> pendingList = new ArrayList<Integer>();
	
	private Map<Integer, String> pendingList = new HashMap<Integer, String>();
	
	private ElevatorController(){};
	
	/**
	 * 
	 * Return the direction of the elevator currently looked at.
	 * @return The string representing the direction.
	 * 
	 * 
	 * 
	 */
	public String getDirection() {
		
		return direction;
	}
	
	/**
	 * 
	 * Return the floor number that the elevator looked at is currently on.
	 * @return The int representing the floor number.
	 * 
	 */
	public int getFloorNum() {
		
		return floorNum;
	}
	
	public void getFloorList() {
		
	}
	
	public Map<Integer, String> getPendingList(){
		return pendingList;
	}
	
	public void addPendDest( int pendDest, String dir ) {
		pendingList.put(pendDest, dir);
	}
	
	/**
	 * 
	 * Creates a single instance of the ElevatorController object. 
	 * @param List of elevators from the created building.
	 * @return
	 * 
	 */
	public static ElevatorController getInstance() {
		
		if (controllerInstance == null) {
			synchronized (ElevatorController.class) {
				if (controllerInstance == null) {
					controllerInstance = new ElevatorController();
				}
			}
		}
		return controllerInstance;
	}
	
	public void setAlg(int alg){
		AlgorithmRequestFactory requestFactory = new AlgorithmRequestFactory();
		requestAlg = requestFactory.getRequestResponse(alg);
		AlgorithmPendingFactory pendingFactory = new AlgorithmPendingFactory();
		PendingAlgorithmSelection pendingAlg = pendingFactory.getPendingResponse(alg);
		
	}
	
	public void setElevatorList(ArrayList<Elevator> arrayList) {
		
		elevatorList = arrayList;
	}
	
	/**
	 * 
	 * Sends a floor request to the specified elevator.
	 * @param 		elevator The number of the elevator you would like to send the request to.
	 * @param 		dest The floor you would like the elevator to go to.
	 * 
	 */
	public void sendRequest( int elevator, int dest ) {
		
		elevatorList.get(elevator - 1).addDest(dest);
	}

	/**
	 * 
	 * Start all the elevators in their individual threads.
	 * 
	 */
	public void startElevators() {
		
		for (int i = 0; i < elevatorList.size(); i++) {
			
			(new Thread (elevatorList.get(i))).start();
		}
		System.out.println(dateFormat.format(new Date()) + "\tAll Elevators started");
	}
	
	/**
	 * 
	 * Stop all of the threads runnning the elevators.
	 * 
	 */
	public void stopElevators(){
		
		for (int i = 0; i < elevatorList.size(); i++) {
			elevatorList.get(i).stop();
		}
		System.out.println(dateFormat.format(new Date()) + "\tAll Elevators stopped");
	}
	
	/**
	 * 
	 * Return the collection of elevators in the current building.
	 * @param 		i The number of the elevator requested.
	 * @return		The ElevatorImpl that is being requested.
	 * 
	 */
	public Elevator getElevator(int i) {
		
		return elevatorList.get(i);
	}
	
	public ArrayList<Elevator> getElevatorList(){
		return elevatorList;
	}
	
	public void setDefaultFloor(int floorNum) {
		
		for(int i = 0; i < elevatorList.size(); i++) {
			
			elevatorList.get(i).setDefault(floorNum);
		}
	}
	
	public void setDefaultFloor(int floorNum, int elevatorNum) {
		
		elevatorList.get(elevatorNum).setDefault(floorNum);
	}
	
	public void request(int floor, String dir) {
		requestAlg.selectElevator(floor, dir);
	}
	
}
