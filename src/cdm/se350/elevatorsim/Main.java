package cdm.se350.elevatorsim;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		String strFl = "";
		String strEl = "";
		String strPpl = "";
		String strSec = "";
		int floors = 0;
		int elevators = 0;
		int people = 0;
		long seconds = 0;
		
		try {
			
			File configXML = new File("config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(configXML);
			doc.getDocumentElement().normalize();
			
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("settings");
			Node nNode = nList.item(0);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				 
				Element eElement = (Element) nNode;
				
				strFl = eElement.getElementsByTagName("floors").item(0).getTextContent();
				strEl = eElement.getElementsByTagName("elevators").item(0).getTextContent();
				strPpl = eElement.getElementsByTagName("persons").item(0).getTextContent();
				strSec = eElement.getElementsByTagName("seconds").item(0).getTextContent();
	 
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		floors = Integer.parseInt(strFl);
		elevators = Integer.parseInt(strEl);
		people = Integer.parseInt(strPpl);
		seconds = Long.parseLong(strSec);
		
		Simulator simulate = new Simulator(floors, elevators, people, seconds);
		simulate.run();
	}
}
