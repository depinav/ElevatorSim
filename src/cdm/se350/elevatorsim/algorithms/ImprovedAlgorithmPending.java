package cdm.se350.elevatorsim.algorithms;

import java.util.ArrayList;
import java.util.Map;

import cdm.se350.elevatorsim.elevator.ElevatorController;
import cdm.se350.elevatorsim.interfaces.PendingResponse;
/**
 * Used to create an implement pending algorithm for pending floors.
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public class ImprovedAlgorithmPending implements PendingResponse {
	
	ElevatorController controller = ElevatorController.getInstance();
	
	Map<Integer, String> pendList = controller.getPendingList();
	
	/**
	 * Handles pending requests using improved algorithm.
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
				//code to put send this request for the new farFloor and maybe sending all other requests with matching directions
				for (Integer key : pendList.keySet() ) {
					if (key > farFloor && controller.getElevator(i).getRequestDir() == "Up" || key < farFloor && controller.getElevator(i).getRequestDir() == "Down"){
						controller.sendRequest(i, key);
					}
				}
			}
		}
	}
}

