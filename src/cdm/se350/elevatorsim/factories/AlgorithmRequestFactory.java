package cdm.se350.elevatorsim.factories;

import cdm.se350.elevatorsim.interfaces.RequestResponse;
import cdm.se350.elevatorsim.algorithms.HieldAlgorithmButtonResponse;
import cdm.se350.elevatorsim.algorithms.ImprovedAlgorithmButtonResponse;
import cdm.se350.elevatorsim.algorithms.ResponseAlgorithmSelection;;

public class AlgorithmRequestFactory {

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
