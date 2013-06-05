package cdm.se350.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cdm.se350.elevatorsim.Building;

public class BuildingTest {


	@Test
	public void construct(){
		System.out.println("Testing building construction");
		Building building = null;
		assertNull(building);
		building = new Building();
		assertNotNull(building);
	}
	
	@Test
	public void getSetMethods(){
		
		Building building = new Building();
		
		System.out.println("Testing Building Get and Set Methods");
		building.setElevators(5);
		building.setFloors(20);
		building.setScale(1);
		assertTrue("Value for elevators: " + building.getElevatorList().size(), building.getElevatorList().size() == 5);
		assertTrue("Value for floors: " + building.getFloorList(), building.getFloorList() == 20);


	}
	
	@Test
	public void testBuildingConstructorErrorHandling(){
		System.out.println("Test Building Constructor Error Handling");
	
		Building building = new Building();
		
		try{
			building.setElevators(-5);
			fail("Allowed negative number of elevators");
		}catch (Exception e){
			System.out.println("Okay. Found: " + e.getMessage());
		}
		
		try{
			building.setFloors(-5);
			fail("Allowed negative number of floors");
		}catch (Exception e2){
			System.out.println("Okay. Found: " + e2.getMessage());
			return;
		}
		
		try{
			building.setScale(-5);
			fail("Allowed negative number for scale");
		}catch (Exception e2){
			System.out.println("Okay. Found: " + e2.getMessage());
			return;
		}
	}
}
