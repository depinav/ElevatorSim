package cdm.se350.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cdm.se350.elevatorsim.elevator.RegElevator;

public class RegElevatorTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void RegElevatorConstructor() {
		
		RegElevator elevator = new RegElevator(1, 3, -1, -2, 3, 6);
	}
	
	@Test
	public void testGetDestinations(){
		RegElevator elevator = new RegElevator(1, 10, 1, 1, 1, 1);
		elevator.addDest(10);
		int i = elevator.getDestList().peek();
		assertEquals(10, i);
	}
	
	@Test
	public void testGetCurrFloor() {
		
		RegElevator elevator = new RegElevator(1, 10, 1, 1, 1, 1);
		elevator.setDefault(3);
		assertEquals(3, elevator.getCurrFloor());
	}
	
	@Test
	public void testGetTravelDir() {
		
		RegElevator elevator = new RegElevator(1, 10, 1, 1, 1, 1);
		new Thread(elevator).start();
		elevator.addDest(5);
		assertEquals("Up", elevator.getTravelDir());
	}
	
	@Test
	public void testGetRequestDir() {
		
		RegElevator elevator = new RegElevator(1, 10, 1, 1, 1, 1);
		new Thread(elevator).start();
		elevator.requestElevator("Down", 7);
		assertEquals("Down", elevator.getRequestDir());
		elevator.stop();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddDest() {
		
		RegElevator elevator = new RegElevator(1, 10, 1, 1, 1, 1);
		elevator.addDest(15);
	}
	
	@Test
	public void testRequestElevator() {
		
		RegElevator elevator = new RegElevator(1, 10, 1, 1, 1, 1);
		elevator.requestElevator("Up", 4);
		int i = elevator.getDestList().peek();
		assertEquals(4, i);
	}
}
