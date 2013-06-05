package cdm.se350.elevatorsim.factories;

import cdm.se350.elevatorsim.interfaces.RequestResponse;
import cdm.se350.elevatorsim.algorithms.HieldAlgorithmButtonResponse;
import cdm.se350.elevatorsim.algorithms.ImprovedAlgorithmButtonResponse;
import cdm.se350.elevatorsim.algorithms.ResponseAlgorithmSelection;;

/**
 * Used to create instances of the selected algorithm
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public class AlgorithmRequestFactory {

	/**
	 * Uses integer to select which algorithm to use.
	 * 1 is set to Hield. 2 is set to improved. Any other number is going to set to Hield
	 * 
	 * @param r		algorithm integer it wants to set
	 * @return		an instance of the selected algorithm
	 */
	public ResponseAlgorithmSelection getRequestResponse(int r) {
		
		ResponseAlgorithmSelection request = null;
		if(r == 1){
			request = new ResponseAlgorithmSelection();
			request.setRequestResponse(new HieldAlgorithmButtonResponse());
		}else if (r == 2){
			request = new ResponseAlgorithmSelection();
			request.setRequestResponse(new ImprovedAlgorithmButtonResponse());
		}else{
			request = new ResponseAlgorithmSelection();
			request.setRequestResponse(new HieldAlgorithmButtonResponse());			
		}
		return request;
	}
}
