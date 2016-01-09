package edu.iut.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import edu.iut.app.Agenda;
import edu.iut.app.ExamEvent;
import edu.iut.app.Person;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//EX 1 Completer la classe 

public class XMLPersistence {

	private Agenda agenda;
	private File file;
	
	public XMLPersistence(Agenda agenda, File file) {
		this.agenda = agenda;
		this.file = file;
	}
	
	public boolean save() {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.newDocument();
			Element root = document.createElement("agenda");

			for (Person person : agenda.getPersons()) {
				Element personXml = document.createElement("person");
				personXml.setAttribute("id", String.valueOf(person.getId()));
				personXml.setAttribute("function", person.getFunction().name());
				personXml.setAttribute("lastname", person.getLastname());
				personXml.setAttribute("firstname", person.getFirstname());
				personXml.setAttribute("phone", person.getPhone());
				personXml.setAttribute("mail", person.getEmail());

				root.appendChild(personXml);
			}

			for (ExamEvent event : agenda) {
				Element eventXml = document.createElement("event");
				eventXml.setAttribute("date", String.valueOf(event.getExamDate().getTime()));
				eventXml.setAttribute("student", String.valueOf(event.getStudent().getId()));

				for(Person person : event.getJury()){
					Element juryXml = document.createElement("jury");
					juryXml.setAttribute("id", String.valueOf(person.getId()));
					eventXml.appendChild(juryXml);
				}

				root.appendChild(eventXml);
			}

			document.appendChild(root);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult output = new StreamResult(file);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, output);

			return true;

		}catch (ParserConfigurationException|TransformerException e){
			e.printStackTrace();
			return false;
		}

	}

	public boolean load(){
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);

			Element root = document.getDocumentElement();

			HashMap<Integer, Person> personsMap = new HashMap<>();

			NodeList persons = root.getElementsByTagName("person");
			for(int i = 0; i < persons.getLength(); i++){
				Node personNode = persons.item(i);
				NamedNodeMap attributes = personNode.getAttributes();

				Person person = new Person();

				for(int att_id = 0; att_id < attributes.getLength(); att_id++){
					Attr attr = (Attr) attributes.item(att_id);
					String a_name = attr.getName().toLowerCase();

					if(a_name.equals("id")){
						person.setId(Integer.valueOf(attr.getValue()));
					}else if(a_name.equals("lastname")){
						person.setLastname(attr.getValue());
					}else if(a_name.equals("firstname")){
						person.setFirstname(attr.getValue());
					}else if(a_name.equals("phone")){
						person.setPhone(attr.getValue());
					}else if(a_name.equals("mail")){
						person.setEmail(attr.getValue());
					}else if(a_name.equals("function")){
						person.setFunction(Person.PersonFunction.valueOf(attr.getValue()));
					}

				}

				personsMap.put(person.getId(), person);
				agenda.getPersons().add(person);
				System.out.println("load : " + person.getFirstname() + " " + person.getLastname() + " (" + person.getFunction() + ")");

			}

			NodeList events = root.getElementsByTagName("event");
			for(int i = 0; i < events.getLength(); i++){
				Node eventNode = events.item(i);
				NamedNodeMap attributes = eventNode.getAttributes();

				ExamEvent event = new ExamEvent();

				for(int att_id = 0; att_id < attributes.getLength(); att_id++){
					Attr attr = (Attr) attributes.item(att_id);
					String a_name = attr.getName().toLowerCase();

					if(a_name.equals("date")){
						event.setExamDate(new Date(Long.valueOf(attr.getValue())));
					}else if(a_name.equals("student")){
						event.setStudent(personsMap.get(Integer.valueOf(attr.getValue())));
					}

					NodeList juryNodelist = eventNode.getChildNodes();
					ArrayList<Person> jury = new ArrayList<>();
					for(int j = 0; j < juryNodelist.getLength(); j++){
						Node juryNode = juryNodelist.item(j);
						if(juryNode.getNodeName().equals("jury")){

							int juryId = Integer.valueOf(juryNode.getAttributes().getNamedItem("id").getNodeValue());
							jury.add(personsMap.get(juryId));
						}
					}

					event.setJury(jury);

				}

				agenda.addCheckedEvent(event);

			}

			return true;

		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
