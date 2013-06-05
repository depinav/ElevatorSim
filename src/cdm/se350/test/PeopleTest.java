package cdm.se350.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import cdm.se350.elevatorsim.Building;
import cdm.se350.elevatorsim.People;

public class PeopleTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Building b = Building.getInstance();
		b.setFloors(5);
		b.setElevators(1, 2, 1, 5, 2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPeopleConstructor() {
		
		People p = new People(-1, -3);
	}
	
	@Test
	public void testGetCurrent() {
		
		People p = new People(3, 5);
		assertEquals(3, p.getCurrent());
	}
	
	
}
