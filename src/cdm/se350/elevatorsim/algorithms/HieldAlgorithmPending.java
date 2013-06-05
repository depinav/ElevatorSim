package cdm.se350.elevatorsim.algorithms;

import java.util.Map;

import cdm.se350.elevatorsim.elevator.ElevatorController;
import cdm.se350.elevatorsim.interfaces.PendingResponse;

/**
 * Used to create an implement Hield's algorithm for pending floors.
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public class HieldAlgorithmPending implements PendingResponse{
	
	ElevatorController controller = ElevatorController.getInstance();
	
	Map<Integer, String> pendList = controller.getPendingList();
	
	/**Handles request that are pending.
	 * 
	 */
	public void PendingRequests(){
		
		int farFloor = 0;
			
		for (int i = 1; i <= controller.getElevatorList().size(); i++){
			if (controller.getElevator(i).getDestList().isEmpty()){
				for ( Integer key : pendList.keySet() ) {
					if (Math.abs(controller.getElevator(i).getCurrFloor() - key) > farFloor){
						farFloor = key;
					}
				}
				controller.sendRequest(i,farFloor);
				for (Integer key : pendList.keySet() ) {
					if (key > farFloor && controller.getElevator(i).getRequestDir() == "Up" || key < farFloor && controller.getElevator(i).getRequestDir() == "Down"){
						controller.sendRequest(i, key);
					}
				}
			}
		}
	}
}
