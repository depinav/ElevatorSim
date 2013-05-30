package cdm.se350.elevatorsim.factories;

import cdm.se350.elevatorsim.People;
import cdm.se350.elevatorsim.interfaces.Person;

public class PersonFactory {

	public Person getPerson(String type, int currFl, int destFl) {
		
		Person person = null;
		if(type.equals("Regular"))
			person = new People(currFl, destFl);
		return person;
	}
}
