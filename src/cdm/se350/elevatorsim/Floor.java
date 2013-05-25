package cdm.se350.elevatorsim;

import cdm.se350.elevatorsim.elevator.ElevatorController;

public class Floor {

	public Floor (){
		
	}
	
	public void pressCallBox(int currFl, int destFl) {
		
		String dir;
		ElevatorController controller = ElevatorController.getInstance();
		
		if( currFl > destFl )
			dir = "Up";
		else
			dir = "Down";
		
		controller.request(currFl, dir);
	}
}
