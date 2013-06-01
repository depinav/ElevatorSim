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
		String strScale = "";
		String strTime = "";
		String strAlg = "";
		int floors = 0;
		int elevators = 0;
		int people = 0;
		int scale = 0;
		long seconds = 0;
		long time = 0;
		int alg = 0;
		
		try {
			
			File configXML = new File("config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(configXML);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("settings");
			Node nNode = nList.item(0);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				 
				Element eElement = (Element) nNode;
				
				strFl = eElement.getElementsByTagName("floors").item(0).getTextContent();
				strEl = eElement.getElementsByTagName("elevators").item(0).getTextContent();
				strPpl = eElement.getElementsByTagName("persons").item(0).getTextContent();
				strScale = eElement.getElementsByTagName("scale").item(0).getTextContent();
				strSec = eElement.getElementsByTagName("seconds").item(0).getTextContent();
				strTime = eElement.getElementsByTagName("time").item(0).getTextContent();
				strAlg = eElement.getElementsByTagName("alg").item(0).getTextContent();
	 
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		floors = Integer.parseInt(strFl);
		elevators = Integer.parseInt(strEl);
		people = Integer.parseInt(strPpl);
		scale = Integer.parseInt(strScale);
		seconds = Long.parseLong(strSec);
		time = Long.parseLong(strTime);
		alg = Integer.parseInt(strAlg);
		
		Simulator simulate = new Simulator(floors, elevators, people, scale, seconds, time, alg);
		simulate.run();
	}
}
