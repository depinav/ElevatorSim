package cdm.se350.elevatorsim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import cdm.se350.elevatorsim.elevator.ElevatorImpl;


public class Building {
	
	private static ArrayList<Floor> floorList = new ArrayList<Floor>();
	private static ArrayList<ElevatorImpl> elevatorList = new ArrayList<ElevatorImpl>();
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private Date date = new Date();
	
	public Building (int floors, int elevators){
		
		try{
			if (floors <= 0) throw new IllegalArgumentException("Floors requires a value greater than 0: " + floors);
		}catch (Exception flrError){
			System.out.println("Error: " + flrError.getMessage());
		}
		
		try{
			if (elevators <= 0) throw new IllegalArgumentException("Elevators requires a value greater than 0: " + elevators);
		}catch (Exception eleError){
			System.out.println("Error: " + eleError.getMessage());
		}
		
		for (int i = 0; i < floors; i++){
			  floorList.add(new Floor());
		}
		
		for (int j = 0; j < elevators; j++){
			elevatorList.add(new ElevatorImpl(j, floorList.size()));
		}
		
		System.out.println(dateFormat.format(date) + "\tBuilding created with " + floors + " floors and " + elevators + " elevators");
	}
	
	public ArrayList<ElevatorImpl> getElevatorList() {
		
		return elevatorList;
	}
	
	public ArrayList<Floor> getFloorList() {
		return floorList;
	}
	
	public void setScale(long _scaled) {
		
		for (int i = 0; i < elevatorList.size(); i++)
			elevatorList.get(i).setScaled(_scaled);
		
		System.out.println(dateFormat.format(date) + "\tScale set to " + _scaled + ":1");
	}
	
	
	public static class BuildingTest extends TestCase {
		private Building building = null;
		
		public BuildingTest (String name) throws Exception {
			super(name);
		}
		
		protected void setUp() throws Exception{
			System.out.println("Setup Building");
			building = new Building (20,5);
		}
		
		protected void tearDown(){
			System.out.println("TearDown Building");
			building = null;
		}
		
		public static Test suite (){
			return new TestSuite(BuildingTest.class);
		}
		
		public void testBuildingElevatorList () throws Exception{
			System.out.println("Testing Building Get and Set Methods");
			
			assertTrue("Value: " + building.getElevatorList().size(), (building.elevatorList.size()) == (5));
			assertTrue("Value: " + building.getFloorList().size(), (building.floorList.size()) == (20));
		}
		
		public void testBuildingConstructorErrorHandling(){
			System.out.println("Test Building Constructor Error Handling");
			
			try{
				Building buildingTest = new Building(-2,5);
				fail("Allowed negative number of floors");
			}catch (Exception e){
				System.out.println("Okay. Found: " + e.getMessage());
			}
			
			try{
				Building buildingTest = new Building(2,-5);
				fail("Allowed negative number of floors");
			}catch (Exception e2){
				System.out.println("Okay. Found: " + e2.getMessage());
				return;
			}
		}
	}
}