package cdm.se350.elevatorsim.algorithms;

import cdm.se350.elevatorsim.elevator.ElevatorController;
import cdm.se350.elevatorsim.interfaces.Elevator;
import cdm.se350.elevatorsim.interfaces.RequestResponse;

/**
 * Used to create an implementation of a type of Elevator.
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public class HieldAlgorithmButtonResponse implements RequestResponse {

	ElevatorController controller = ElevatorController.getInstance();
	
	public void ElevatorRequest(int floor, String dir){
		
		
		for (int i = 0; i < controller.getElevatorList().size(); i++){
			String t = controller.getElevator(i).getTravelDir();
			if (floor == controller.getElevator(i).getCurrFloor() && controller.getElevator(i).getDestList().isEmpty()){
				controller.getElevator(i).addDest(floor);
				break;
				
			}else if(!controller.getElevator(i).getDestList().isEmpty()){
				if ((controller.getElevator(i).getTravelDir().equals("Up") && controller.getElevator(i).getCurrFloor() < floor) || (controller.getElevator(i).getTravelDir().equals("Down") && controller.getElevator(i).getCurrFloor() > floor)){
				
					controller.getElevator(i).addDest(floor);
					break;
				}
				
			}else if ( (controller.getElevator(i).getDestList().isEmpty()) ){
				controller.getElevator(i).addDest(floor);
				break;
				
			}else{
				controller.addPendDest(floor, dir);
			}
		}
	}
}
