package cdm.se350.elevatorsim.interfaces;

import java.util.PriorityQueue;

public interface ElevatorInt {
	
	void arrived() throws InterruptedException;
	void addDest(int newDest);
	public PriorityQueue<Integer> getDestList();
}
