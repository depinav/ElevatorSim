package cdm.se350.elevatorsim.interfaces;

import java.util.PriorityQueue;

/**
 * Used to create an implementation of a type of Elevator.
 * 
 * @author 		Victor DePina
 * @author 		Edric Delleola
 * @since 		Version 1.0
 * 
 */
public interface Elevator extends Runnable{
	
	/**
	 * 
	 * This method is used to initiate specific actions when an elevator arrives to a specified floor.
	 * @throws InterruptedException
	 * 
	 */
	void arrived() throws InterruptedException;
	
	/**
	 * 
	 * This method is used to add a destination to the elevators destination list.
	 * @param 		newDest		An int representing the floor to send the elevator to.
	 */
	void addDest(int newDest);
	
	/**
	 * 
	 * This method returns the destination list of the elevator.
	 * @return		The collection of the elevators destination list.
	 * 
	 */
	PriorityQueue<Integer> getDestList();

	void setScaled(long _scaled);

	void stop();

	int getCurrFloor();

	String getTravelDir();
	
	public void setDefault(int floorNum);
	
	public void addPassenger();
	
	public void removePassenger();
	
	void requestElevator( String dir, int floor );

	String getRequestDir();

	boolean isFull();
}
