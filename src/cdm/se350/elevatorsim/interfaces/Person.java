package cdm.se350.elevatorsim.interfaces;

public interface Person extends Runnable {
	
	void elevatorArrived();
	void enterElevator(int elevator);
	void exitElevator();
	int getCurrent();
	int getNumber();
	void stop();
}
