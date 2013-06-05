package cdm.se350.elevatorsim.algorithms;

import cdm.se350.elevatorsim.elevator.ElevatorController;
import cdm.se350.elevatorsim.interfaces.RequestResponse;

/**
 * Used to create an implement Hield's algorithm for call box response.
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public class HieldAlgorithmButtonResponse implements RequestResponse {

	ElevatorController controller = ElevatorController.getInstance();
	
	/**Algorithm to handle call box requests 
	 * 
	 */
	public void ElevatorRequest(int floor, String dir){
		
		AlgoLoop : while (true){
			
			for (int i = 0; i < controller.getElevatorList().size(); i++){
				if (floor == controller.getElevator(i).getCurrFloor() && controller.getElevator(i).getDestList().isEmpty()){
					controller.getElevator(i).requestElevator(dir, floor);
//					System.out.println("1 Scenario Sending elevator: Elevator " + i);
					break AlgoLoop;
				}
			}
			
			for (int i = 0; i < controller.getElevatorList().size(); i++){		
				if(!controller.getElevator(i).getDestList().isEmpty()){
					if (!controller.getElevator(i).isFull() && (controller.getElevator(i).getTravelDir().equals("Up") && controller.getElevator(i).getCurrFloor() < floor) && dir == controller.getElevator(i).getRequestDir() || !controller.getElevator(i).isFull() && (controller.getElevator(i).getTravelDir().equals("Down") && controller.getElevator(i).getCurrFloor() > floor)){
						controller.getElevator(i).requestElevator(dir, floor);
//						System.out.println("2 Scenario Sending elevator: Elevator " + i);
						break AlgoLoop;
					}
					
				}
			}
			
			for (int i = 0; i < controller.getElevatorList().size(); i++){
				if ( (controller.getElevator(i).getDestList().isEmpty()) ){
					controller.getElevator(i).requestElevator(dir, floor);
//					System.out.println("3 Scenario Sending elevator: Elevator " + i);
					break AlgoLoop;
					
				}
			}
			System.out.println("Adding to pending list ");
			controller.addPendDest(floor, dir);
			break AlgoLoop;
		}
	}
}
