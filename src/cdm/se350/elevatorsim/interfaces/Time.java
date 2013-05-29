package cdm.se350.elevatorsim.interfaces;

/**
 * Used to add time based methods.
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */

public interface Time {
	
	/**
	 * 
	 * This method is used to convert seconds to milliseconds 
	 * @param 		sec		The int representing initial seconds.
	 * @return		The converted seconds into milliseconds.
	 * 
	 */
	long toMilli(long sec);
}
