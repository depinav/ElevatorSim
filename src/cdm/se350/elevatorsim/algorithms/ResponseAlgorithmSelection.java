package cdm.se350.elevatorsim.algorithms;

import cdm.se350.elevatorsim.interfaces.RequestResponse;

public class ResponseAlgorithmSelection {

	private RequestResponse strategy;
	
	public void setRequestResponse(RequestResponse strategy){
		this.strategy = strategy; 
	}
	
	public void selectElevator(int floor, String dir ){
		strategy.ElevatorRequest(floor, dir);
	}
}

/*
to set algorithm to hield algorithm

ResponseAlgorithmSelection hield = new ResponseAlgorithmSelection();
hield.setRequestResponse(new HieldAlgorithmButtonResponse());

*/