package cdm.se350.elevatorsim.algorithms;

import java.util.ArrayList;
import java.util.Map;

import cdm.se350.elevatorsim.elevator.ElevatorController;

public class ImprovedAlgorithmPending {
	
	ElevatorController controller = ElevatorController.getInstance();
	
	Map<Integer, String> pendList = controller.getPendingList();
	
	public void PendingRequests(int floor, String dir){
		
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
					if (key > farFloor && dir == "Up" || key < farFloor && dir == "Down"){
						controller.sendRequest(i, key);
					}
				}
			}
		}
	}
}

