/**
 * 
 */
package utilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
import shared.model.User;

/**
 * Class which parses a given XML document and uses the information to populate the databasae
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
	 * If passed the xml file location this will automatically parse through the entire xml and fill
	 * the database.
	 * 
	 * @param xmllocation
	 */
	public XMLParser(String xmllocation) {
		database = new Database();
		xml = new File(xmllocation);
		// get the parent folder and its absolute path... Set it as root.
		root = xml.getParentFile().getAbsolutePath();
		//System.out.println(root);
		//Lets try to copy over the directory to our file place.
		File source = new File(root);
		System.out.println("Source: " + source);
		File target = new File("./files");
		try {
			FileUtils.copyDirectory(source, target);
           // FileUtils.copyFileToDirectory(srcFile, destDir);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// TODO Auto-generated constructor stub
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
		//System.out.println("parse USERS");

		NodeList userlist = doc.getElementsByTagName("user");
		for (int i = 0; i < userlist.getLength(); ++i) {

			Element userElem = (Element) userlist.item(i);

			Element usernameElem = (Element) userElem.getElementsByTagName("username").item(0);
			Element passwordElem = (Element) userElem.getElementsByTagName("password").item(0);
			Element firstnameElem = (Element) userElem.getElementsByTagName("firstname").item(0);
			Element lastnameElem = (Element) userElem.getElementsByTagName("lastname").item(0);
			Element emailElem = (Element) userElem.getElementsByTagName("email").item(0);
			Element indexedrecordsElem = (Element) userElem.getElementsByTagName("indexedrecords").item(0);

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
				indexedrecords = Integer.valueOf(indexedrecordsElem.getTextContent());

			// System.out.println(username + ", " + password + ", " + firstname
			// + ", " + lastname + ", " + email + ", " + indexedrecords);

			// OK we have each of the users at this point... Lets add them to
			// the database.
			// maybe make a shared user model class with the information and
			// then add it to the database.
			User user = new User(username, password, firstname, lastname, email, indexedrecords, -1);
			// ok we have a new user... now what? UserDAO!!!!
			try {
				database.getUserdao().insert(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This function parses the projects and then calls the parseFields and parseImages functions.
	 * 
	 * @param doc
	 */
	public void parseProjects(Document doc) {
		//System.out.println("parse PROJECTS");

		// TODO: do we need to do anything with the Projects and Users
		// supertags??
		NodeList projectlist = doc.getElementsByTagName("project");
		for (int i = 0; i < projectlist.getLength(); ++i) {

			Element projectElem = (Element) projectlist.item(i);

			Element titleElem = (Element) projectElem.getElementsByTagName("title").item(0);
			Element recordsperimageElem = (Element) projectElem.getElementsByTagName("recordsperimage").item(0);
			Element firstycoordElem = (Element) projectElem.getElementsByTagName("firstycoord").item(0);
			Element recordheightElem = (Element) projectElem.getElementsByTagName("recordheight").item(0);

			// fields we need to parse...........
			parseFields(projectElem, i);
			// images we need to parse...........
			parseImages(projectElem, i);

			String title = null;
			int recordsperimage = -1;
			int firstycoord = -1;
			int recordheight = -1;

			if (titleElem != null)
				title = titleElem.getTextContent();
			if (recordsperimageElem != null)
				recordsperimage = Integer.valueOf(recordsperimageElem.getTextContent());
			if (firstycoordElem != null)
				firstycoord = Integer.valueOf(firstycoordElem.getTextContent());
			if (recordheightElem != null)
				recordheight = Integer.valueOf(recordheightElem.getTextContent());

			// OK we have each of the projects at this point... Lets add them to
			// the database.
			// System.out.println(title + ", " + recordsperimage + ", "
			// + firstycoord + ", " + recordheight);

			Project project = new Project(-1, title, recordsperimage, firstycoord, recordheight);
			try {
				database.getProjectdao().insert(project);
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
	public void parseFields(Element projectElem, int projectid) {
		//System.out.println("parse FIELDS");
		NodeList fieldlist = projectElem.getElementsByTagName("field");
		for (int i = 0; i < fieldlist.getLength(); ++i) {
			Element fieldElem = (Element) fieldlist.item(i);

			Element titleElem = (Element) fieldElem.getElementsByTagName("title").item(0);
			Element xcoordElem = (Element) fieldElem.getElementsByTagName("xcoord").item(0);
			Element widthElem = (Element) fieldElem.getElementsByTagName("width").item(0);
			Element helphtmlElem = (Element) fieldElem.getElementsByTagName("helphtml").item(0);
			Element knowndataElem = (Element) fieldElem.getElementsByTagName("knowndata").item(0);
			
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

			// System.out.println(title + ", " + xcoord + ", " + width + ", "
			// + helphtml + ", " + knowndata);

			Field field = new Field(-1, title, xcoord, width, helphtml, knowndata, i + 1,  projectid + 1);
			try {
				database.getFielddao().insert(field);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * This function parses the images without our Records.XML
	 * 
	 * @param projectElem
	 * @throws
	 */
	public void parseImages(Element projectElem, int projectid) {
		//System.out.println("parse Images");

		NodeList imagelist = projectElem.getElementsByTagName("image");
		for (int i = 0; i < imagelist.getLength(); ++i) {
			Element imageElem = (Element) imagelist.item(i);

			Element fileElem = (Element) imageElem.getElementsByTagName("file").item(0);

			String file = null;
			if (fileElem != null)
				file = fileElem.getTextContent();

			// System.out.println(file);
			Image image = new Image(-1, file, projectid + 1);
			try {
				database.getImagedao().insert(image);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
