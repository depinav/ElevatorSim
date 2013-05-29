package cdm.se350.elevatorsim.algorithms;

import cdm.se350.elevatorsim.elevator.ElevatorController;
import java.util.PriorityQueue;

/**
 * Used to create an implementation of a type of Elevator.
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public class HieldAlgorithmButtonResponse {

	ElevatorController controller = ElevatorController.getInstance();
	
	void ElevatorRequest(int floor, String dir){
		
		for (int i = 1; i <= controller.getElevatorList().size(); i++){
			
			if (floor == controller.getElevator(i).getCurrFloor() && controller.getElevator(i).getDestList().isEmpty()){
				controller.sendRequest(i, floor);
				break;
				
			}else if( (controller.getElevator(i).getTravelDir().equals("Up") && controller.getElevator(i).getCurrFloor() < floor) || (controller.getElevator(i).getTravelDir().equals("Down") && controller.getElevator(i).getCurrFloor() > floor) ){
				controller.sendRequest(i, floor);
				break;
				
			}else if ( (controller.getElevator(i).getDestList().isEmpty()) ){
				controller.sendRequest(i, floor);
				
			}else{
				controller.addPendDest(floor, dir);
			}
		}
	}
}
