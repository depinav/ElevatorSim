package cdm.se350.elevatorsim.factories;

import cdm.se350.elevatorsim.algorithms.HieldAlgorithmPending;
import cdm.se350.elevatorsim.algorithms.ImprovedAlgorithmPending;
import cdm.se350.elevatorsim.algorithms.PendingAlgorithmSelection;

/**
 * Used to create instances of the selected algorithm
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public class AlgorithmPendingFactory {
	
	/**
	 * Uses integer to select which algorithm to use.
	 * 1 is set to Hield. 2 is set to improved. Any other number is going to set to Hield
	 * 
	 * @param r		algorithm integer it wants to set
	 * @return		an instance of the selected algorithm
	 */
	public PendingAlgorithmSelection getPendingResponse(int r) {
			
			PendingAlgorithmSelection pending = null;
			if(r == 1){
				pending = new PendingAlgorithmSelection();
				pending.setPendingResponse(new HieldAlgorithmPending());
			}else if (r == 2){
				pending = new PendingAlgorithmSelection();
				pending.setPendingResponse(new ImprovedAlgorithmPending());
			}else{
				pending = new PendingAlgorithmSelection();
				pending.setPendingResponse(new HieldAlgorithmPending());			
			}
			return pending;

	}
}
