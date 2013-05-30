package cdm.se350.elevatorsim.algorithms;

import cdm.se350.elevatorsim.interfaces.PendingResponse;

public class PendingAlgorithmSelection {

	private PendingResponse strategy;
	
	public void setRequestResponse(PendingResponse strategy){
		this.strategy = strategy; 
	}
	
	public void selectElevator(int floor, String dir ){
		strategy.PendingRequests(floor, dir);
	}
}

/*
to set algorithm to hield algorithm

PendingAlgorithmSelection hield = new PendingAlgorithmSelection();
hield.setRequestResponse(new HieldAlgorithmButtonResponse());

*/
