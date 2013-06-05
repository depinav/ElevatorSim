package cdm.se350.elevatorsim.interfaces;

/**
 * Used to send pending responses
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public interface PendingResponse {

	/**
	 * This method is used to send pending responses
	 * @param floor		floor requesting elevator
	 * @param dir		travel direction requested
	 */
	public void PendingRequests(int floor, String dir);
}
