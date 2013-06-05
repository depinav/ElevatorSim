package cdm.se350.elevatorsim.algorithms;

import cdm.se350.elevatorsim.interfaces.PendingResponse;

/**
 * Used to select which pending algorithm to use.
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public class PendingAlgorithmSelection {

	private PendingResponse strategy;
	
	/**
	 * sets the pending response algorithm
	 * @param strategy		pending algorithm strategy
	 */
	public void setPendingResponse(PendingResponse strategy){
		this.strategy = strategy; 
	}
	
	/**
	 * Sends pending requests
	 * 
	 * @param floor		Floor of the request
	 * @param dir		direction of travel
	 */
	public void pending(int floor, String dir ){
		strategy.PendingRequests(floor, dir);
	}	
}
