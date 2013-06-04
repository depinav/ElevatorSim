package cdm.se350.elevatorsim.interfaces;

public interface Person extends Runnable {
	
	void elevatorArrived(int floor, int _elevator);
	void enterElevator();
	void exitElevator();
	int getCurrent();
	int getNumber();
	void stop();
	boolean inElevator();
}
