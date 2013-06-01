package cdm.se350.elevatorsim.algorithms;

import cdm.se350.elevatorsim.elevator.ElevatorController;
import cdm.se350.elevatorsim.interfaces.RequestResponse;

import java.util.PriorityQueue;

/**
 * Used to create an implementation of a type of Elevator.
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public class ImprovedAlgorithmButtonResponse implements RequestResponse {

	ElevatorController controller = ElevatorController.getInstance();
	
	public void ElevatorRequest(int floor, String dir){
		
		for (int i = 1; i <= controller.getElevatorList().size(); i++){
			
			if (floor == controller.getElevator(i).getCurrFloor() && controller.getElevator(i).getDestList().isEmpty()){
				controller.sendRequest(i, floor);
				break;
				
			}else if( (controller.getElevator(i).getTravelDir().equals("Up") && controller.getElevator(i).getCurrFloor() < floor) || (controller.getElevator(i).getTravelDir().equals("Down") && controller.getElevator(i).getCurrFloor() > floor) ){
				int idleEle = 0;
				//checks for how many elevators are idling
				for (int j = 1; j <= controller.getElevatorList().size(); j++){
					
					if ( controller.getElevator(j).getDestList().isEmpty() ){
						idleEle++;
					}
				}
				//if more than half the elevators are idle and the elevator already is making more than 3 stops then send a new one
				if (idleEle > ( (controller.getElevatorList().size() )/2 ) && controller.getElevator(i).getDestList().size() > 3){
					int closestFl = 0;
					int bestEle = -1 ;
					for (int k = 1; k <= controller.getElevatorList().size(); k++){
						if (controller.getElevator(k).getCurrFloor() < closestFl && controller.getElevator(k).getDestList().isEmpty() ){
							closestFl = controller.getElevator(k).getCurrFloor();
							bestEle = k;
						}
						controller.sendRequest(bestEle, floor);
						break;
					}
				}
				//if not enough elevators idle or no more than 3 stops are made then sends the elevator already going in that direction
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
