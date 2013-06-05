package cdm.se350.elevatorsim.interfaces;

/**
 * Used to send call box responses
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public interface RequestResponse{

	/**
	 * This method is used to send call box responses
	 * @param floor		floor requesting elevator
	 * @param dir		travel direction requested
	 */
	public void ElevatorRequest(int floor, String dir);
	
}
