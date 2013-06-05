package cdm.se350.elevatorsim.algorithms;

import cdm.se350.elevatorsim.interfaces.RequestResponse;

/**
 * Used to select which call box response algorithm to use.
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public class ResponseAlgorithmSelection {

	private RequestResponse strategy;
	
	/**
	 * sets the button response algorithm
	 * @param strategy		response algorithm strategy
	 */
	public void setRequestResponse(RequestResponse strategy){
		this.strategy = strategy; 
	}
	
	/**
	 * Sends button response requests
	 * 
	 * @param floor		Floor of the request
	 * @param dir		direction of travel
	 */
	public void selectElevator(int floor, String dir ){
		strategy.ElevatorRequest(floor, dir);
	}
}