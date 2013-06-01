package cdm.se350.elevatorsim.algorithms;

import cdm.se350.elevatorsim.interfaces.PendingResponse;

public class PendingAlgorithmSelection {

	private PendingResponse strategy;
	
	public void setPendingResponse(PendingResponse strategy){
		this.strategy = strategy; 
	}
	
	public void pending(int floor, String dir ){
		strategy.PendingRequests(floor, dir);
	}	
}



/*
to set algorithm to hield algorithm

PendingAlgorithmSelection improved = new PendingAlgorithmSelection();
improved.setRequestResponse(new ImprovedPendingButtonResponse());

improved.pending(floor, dir); --something like this

*/
