package cdm.se350.elevatorsim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import cdm.se350.elevatorsim.factories.ElevatorFactory;
import cdm.se350.elevatorsim.interfaces.Elevator;
import cdm.se350.elevatorsim.interfaces.Time;


public final class Building implements Time {
	
	//private static ArrayList<Floor> floorList = new ArrayList<Floor>();
	private static Floor floor = null;
	private static int floors;
	private static ArrayList<Elevator> elevatorList = new ArrayList<Elevator>();
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private volatile static Building buildingInstance;
	
	public Building (){ System.out.println(dateFormat.format(new Date()) + "\tBuilding created with: "); }
	
	public static Building getInstance() {

		if (buildingInstance == null) {
			synchronized (Building.class) {
				if (buildingInstance == null) {
					buildingInstance = new Building();
				}
			}
		}
		return buildingInstance;
	}
	
	public void setFloors(int totalFloors) {
		
		
		try{
			if (totalFloors <= 0) 
				throw new IllegalArgumentException("Floors requires a value greater than 0: " + totalFloors);
			else {
				floors = totalFloors;
				floor = new Floor();
//				for (int i = 0; i < totalFloors; i++)
//					  floorList.add(new Floor());
			}
		}catch (Exception flrError){
			System.out.println("Error: " + flrError.getMessage());
		}
		
		System.out.println(dateFormat.format(new Date()) + "\t" +  totalFloors + " Floors");
	}
	
	public Floor getFloor() {
		
		return floor;
	}
	
	public void setElevators(int totalElevators) {
		
		try{
			if (totalElevators <= 0) 
				throw new IllegalArgumentException("Elevators requires a value greater than 0: " + totalElevators);
			else
				for (int j = 0; j < totalElevators; j++){
					ElevatorFactory elevatorFactory = new ElevatorFactory();
					elevatorList.add(elevatorFactory.getElevator("Regular", j, floors/*floorList.size()*/));
				}
		}catch (Exception eleError){
			System.out.println("Error: " + eleError.getMessage());
		}
		
		System.out.println(dateFormat.format(new Date()) + "\t" +  totalElevators + " Elevators");
	}
	
	public ArrayList<Elevator> getElevatorList() {
		
		return elevatorList;
	}
	
	public int getFloorList() {
		
		return floors;
	}
	
	public void setScale(long _scaled) {
		
		for (int i = 0; i < elevatorList.size(); i++)
			elevatorList.get(i).setScaled(_scaled);
		
		System.out.println(dateFormat.format(new Date()) + "\tScale set to " + _scaled + ":1");
	}
	
	public void addPersons(int people, long sec) {
		
		floor.createPeople(people, sec);
	}
	
	public void startPeople() {
		
		floor.runPeople();
	}
	
	public void stopPeople() {
		
		floor.endPeople();
	}

	public long toMilli(long sec) {
		
		return sec * 1000;
	}
	
	
	/*public static class BuildingTest extends TestCase {
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
	} */
}