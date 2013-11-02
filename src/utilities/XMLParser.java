package utilities;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import server.database.Database;
import shared.model.Field;
import shared.model.Image;
import shared.model.Project;
import shared.model.Record;
import shared.model.User;
import shared.model.Value;

/**
 * Class which parses a given XML document and uses the information to populate
 * the databasae
 * 
 * @author Kevin
 * 
 */
public class XMLParser {

	File xml;
	String root = null;
	Database database;

	/**
	 * Default Constructor
	 */
	public XMLParser() {
		// TODO Auto-generated constructor stub
		database = null;
	}

	/**
	 * If passed the xml file location this will automatically parse through the
	 * entire xml and fill the database.
	 * 
	 * @param xmllocation
	 */
	public XMLParser(String xmllocation, Database database) {
		this.database = database;
		xml = new File(xmllocation);
		// get the parent folder and its absolute path... Set it as root.
		root = xml.getParentFile().getAbsolutePath();
		// Lets try to copy over the directory to our file place.
		File source = new File(root);
		File target = new File("./ImportedData");
		try {
			FileUtils.copyDirectory(source, target);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		root = "ImportedData/";
		/*
		 * Need to parse the XML document and populate the database with the information. Create new
		 * users and new projects...
		 */
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			File file = new File(xmllocation);
			Document doc = builder.parse(file);
			parseUsers(doc);
			parseProjects(doc);
			System.out.println("Done parsing XML");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This function parses the Users
	 * 
	 * @param doc
	 */
	public void parseUsers(Document doc) {

		NodeList userlist = doc.getElementsByTagName("user");
		for (int i = 0; i < userlist.getLength(); ++i) {

			Element userElem = (Element) userlist.item(i);

			Element usernameElem = (Element) userElem.getElementsByTagName(
					"username").item(0);
			Element passwordElem = (Element) userElem.getElementsByTagName(
					"password").item(0);
			Element firstnameElem = (Element) userElem.getElementsByTagName(
					"firstname").item(0);
			Element lastnameElem = (Element) userElem.getElementsByTagName(
					"lastname").item(0);
			Element emailElem = (Element) userElem
					.getElementsByTagName("email").item(0);
			Element indexedrecordsElem = (Element) userElem
					.getElementsByTagName("indexedrecords").item(0);

			String username = null;
			String password = null;
			String firstname = null;
			String lastname = null;
			String email = null;
			int indexedrecords = 0;

			if (usernameElem != null)
				username = usernameElem.getTextContent();
			if (passwordElem != null)
				password = passwordElem.getTextContent();
			if (firstnameElem != null)
				firstname = firstnameElem.getTextContent();
			if (lastnameElem != null)
				lastname = lastnameElem.getTextContent();
			if (emailElem != null)
				email = emailElem.getTextContent();
			if (indexedrecordsElem != null)
				indexedrecords = Integer.valueOf(indexedrecordsElem
						.getTextContent());

			User user = new User(username, password, firstname, lastname,
					email, indexedrecords, -1);
			// ok we have a new user... now what? UserDAO!!!!
			try {
				database.getUserdao().insert(user, database);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This function parses the projects and then calls the parseFields and
	 * parseImages functions.
	 * 
	 * @param doc
	 */
	public void parseProjects(Document doc) {

		NodeList projectlist = doc.getElementsByTagName("project");
		for (int i = 0; i < projectlist.getLength(); ++i) {

			Element projectElem = (Element) projectlist.item(i);

			Element titleElem = (Element) projectElem.getElementsByTagName(
					"title").item(0);
			Element recordsperimageElem = (Element) projectElem
					.getElementsByTagName("recordsperimage").item(0);
			Element firstycoordElem = (Element) projectElem
					.getElementsByTagName("firstycoord").item(0);
			Element recordheightElem = (Element) projectElem
					.getElementsByTagName("recordheight").item(0);

			// fields we need to parse...........
			int numfields = parseFields(projectElem, i);
			// images we need to parse...........
			parseImages(projectElem, i);

			String title = null;
			int recordsperimage = -1;
			int firstycoord = -1;
			int recordheight = -1;

			if (titleElem != null)
				title = titleElem.getTextContent();
			if (recordsperimageElem != null)
				recordsperimage = Integer.valueOf(recordsperimageElem
						.getTextContent());
			if (firstycoordElem != null)
				firstycoord = Integer.valueOf(firstycoordElem.getTextContent());
			if (recordheightElem != null)
				recordheight = Integer.valueOf(recordheightElem
						.getTextContent());

			Project project = new Project(-1, title, recordsperimage,
					numfields, firstycoord, recordheight);
			try {
				database.getProjectdao().insert(project, database);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// lets add the project
		}
	}

	/**
	 * This functions parses the fields within our Records.XML
	 * 
	 * @param projectElem
	 */
	public int parseFields(Element projectElem, int projectid) {
		NodeList fieldlist = projectElem.getElementsByTagName("field");
		for (int i = 0; i < fieldlist.getLength(); ++i) {
			Element fieldElem = (Element) fieldlist.item(i);

			Element titleElem = (Element) fieldElem.getElementsByTagName(
					"title").item(0);
			Element xcoordElem = (Element) fieldElem.getElementsByTagName(
					"xcoord").item(0);
			Element widthElem = (Element) fieldElem.getElementsByTagName(
					"width").item(0);
			Element helphtmlElem = (Element) fieldElem.getElementsByTagName(
					"helphtml").item(0);
			Element knowndataElem = (Element) fieldElem.getElementsByTagName(
					"knowndata").item(0);

			String title = null;
			int xcoord = -1;
			int width = -1;
			String helphtml = null;
			String knowndata = null;

			if (titleElem != null)
				title = titleElem.getTextContent();
			if (xcoordElem != null)
				xcoord = Integer.valueOf(xcoordElem.getTextContent());
			if (widthElem != null)
				width = Integer.valueOf(widthElem.getTextContent());
			if (helphtmlElem != null)
				helphtml = helphtmlElem.getTextContent();
			if (knowndataElem != null)
				knowndata = knowndataElem.getTextContent();

			Field field = new Field(-1, title, xcoord, width, helphtml,
					knowndata, i + 1, projectid + 1);
			try {
				database.getFielddao().insert(field, database);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return fieldlist.getLength();
	}

	/**
	 * This function parses the images without our Records.XML
	 * 
	 * @param projectElem
	 * @throws
	 */
	public void parseImages(Element projectElem, int projectid) {

		NodeList imagelist = projectElem.getElementsByTagName("image");

		for (int i = 0; i < imagelist.getLength(); ++i) {
			Element imageElem = (Element) imagelist.item(i);

			Element fileElem = (Element) imageElem.getElementsByTagName("file")
					.item(0);
			Element recordsElem = (Element) imageElem.getElementsByTagName(
					"records").item(0);

			String file = null;
			if (fileElem != null)
				//file = fileElem.getTextContent();
				file = root + fileElem.getTextContent();

			boolean hasbeenindexed = false;
			if(recordsElem != null) {
				hasbeenindexed = true;
			}
			Image image = new Image(-1, file, projectid + 1, hasbeenindexed);
			int imageid = 0;
			try {
				imageid = database.getImagedao().insert(image, database);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (recordsElem != null) {
				parseRecords(imageElem, imageid);
			}

		}
	}

	public void parseRecords(Element imageElem, int imageid) {

		NodeList records = imageElem.getElementsByTagName("records");
		Element recordsElem = (Element) records.item(0);

		NodeList recordlist = recordsElem.getElementsByTagName("record");

		for (int i = 0; i < recordlist.getLength(); ++i) { // number of rows. +1
			Element recordElem = (Element) recordlist.item(i);

			Element valuesElem = (Element) recordElem.getElementsByTagName(
					"values").item(0);

			int rownumber = i + 1;
			boolean hasvalues = true;
			Record record = new Record(-1, rownumber, imageid, hasvalues);

			int recordid = 0;

			try {
				recordid = database.getRecorddao().insert(record, database);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (valuesElem != null) {
				parseValues(valuesElem, rownumber, recordid, imageid);
			}
		}
	}

	public void parseValues(Element valuesElem, int rownum, int imageid,
			int recordid) {

		NodeList valuelist = valuesElem.getElementsByTagName("value");

		for (int i = 0; i < valuelist.getLength(); ++i) {

			Element valueElem = (Element) valuelist.item(i);

			String value_string = valueElem.getTextContent();

			int fieldnum = i + 1;

			Value value = new Value(-1, value_string, fieldnum, rownum,
					imageid, recordid);

			try {
				database.getValuedao().insert(value, database);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
