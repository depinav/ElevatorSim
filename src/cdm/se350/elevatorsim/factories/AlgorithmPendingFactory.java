package cdm.se350.elevatorsim.factories;

import cdm.se350.elevatorsim.algorithms.HieldAlgorithmPending;
import cdm.se350.elevatorsim.algorithms.ImprovedAlgorithmPending;
import cdm.se350.elevatorsim.algorithms.PendingAlgorithmSelection;

public class AlgorithmPendingFactory {
	
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
