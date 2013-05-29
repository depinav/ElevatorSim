package cdm.se350.elevatorsim;


public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Simulator simulate = new Simulator(15, 6, 3, 10);
		simulate.run();
	}
}
