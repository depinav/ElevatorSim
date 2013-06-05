package cdm.se350.elevatorsim.algorithms;

import cdm.se350.elevatorsim.elevator.ElevatorController;
import cdm.se350.elevatorsim.interfaces.Elevator;
import cdm.se350.elevatorsim.interfaces.RequestResponse;


/**
 * Used to create an implement an improved algorithm for call box response that takes into account idle elevators and elevators with a few destinations already. 
 * If more than half the elevators are null and elevators going the same direction have more than 3 destinations already this sends a new elevator.
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public class ImprovedAlgorithmButtonResponse implements RequestResponse {

	ElevatorController controller = ElevatorController.getInstance();
	
	/**Algorithm to handle call box requests
	 * 
	 */
	public void ElevatorRequest(int floor, String dir){
		
		int idleEle = 0;
		
		AlgoLoop : while (true){
			for (int i = 0; i < controller.getElevatorList().size(); i++){
				if (floor == controller.getElevator(i).getCurrFloor() && controller.getElevator(i).getDestList().isEmpty()){
					controller.getElevator(i).requestElevator(dir, floor);
//					System.out.println("1 Scenario Sending elevator: Elevator " + i);
					break AlgoLoop;
				}
			}
			
			for (int l = 0; l < controller.getElevatorList().size(); l++){		
				if(!controller.getElevator(l).getDestList().isEmpty()){
					idleEle++;
				}
			for (int i = 0; i < controller.getElevatorList().size(); i++){
				if (idleEle > ( (controller.getElevatorList().size() )/2 ) && controller.getElevator(i).getDestList().size() > 3){
					int closestFl = 0;
					int bestEle = -1;
					for (int k = 1; k <= controller.getElevatorList().size(); k++){
						if (controller.getElevator(k).getCurrFloor() < closestFl && controller.getElevator(k).getDestList().isEmpty() ){
							closestFl = controller.getElevator(k).getCurrFloor();
							bestEle = k;
						}
						controller.getElevator(i).requestElevator(dir, floor);
						break AlgoLoop;
					}
				}
			}
			
			for (int i = 0; i < controller.getElevatorList().size(); i++){		
				if(!controller.getElevator(i).getDestList().isEmpty()){
					
					if( !controller.getElevator(i).isFull() ) {
						
						if( ("Up".equals(controller.getElevator(i).getRequestDir()) && "Up".equals(controller.getElevator(i).getTravelDir())) && (floor > controller.getElevator(i).getCurrFloor()) ) {
							
							controller.getElevator(i).requestElevator(dir, floor);
							break AlgoLoop;
						} else if( ("Down".equals(controller.getElevator(i).getRequestDir()) && "Down".equals(controller.getElevator(i).getTravelDir())) && (floor < controller.getElevator(i).getCurrFloor()) ) {
							
							controller.getElevator(i).requestElevator(dir, floor);
							break AlgoLoop;
						}
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
}
