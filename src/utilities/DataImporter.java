package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import server.database.Database;

/**
 * This class reads in the Records file and copies all data over to secure location. It also calls
 * the parseXML class.
 * 
 * @author Kevin
 * 
 */
public class DataImporter {

	Database database;

	/**
	 * Default Constructor
	 * 
	 */
	public DataImporter() {
		// TODO Auto-generated constructor stub
		database = new Database();
	}

	/**
	 * @param full
	 *            path to XML file
	 */
	public static void main(String[] args) {
		System.out.println("main");
		try {
			// This standalone program takes in one argument which is the
			// relative or absolute path to the XML file.
			String pathtoxml = args[0];

			// First delete all tables from SQLite database and directory
			// structure

			DataImporter di = new DataImporter();
			di.database.startTransaction();
			di.createAllTables();

			// Second populate the database with the XML contents
			XMLParser xmlparser = new XMLParser(pathtoxml);
			// Third copy all files (knownsdata, helphtml, and images) to the
			// servers directory
		} catch (Exception e) {
			System.out.println("USAGE: java DataImporter [path_to_xml_file]");
			// throw e;
		}
	}

	private void createAllTables() throws SQLException {
		// TODO : figure out how to use this stuff??

		System.out.println("create all tables");

		Statement stmt = null;
		Scanner s = null;
		try {
			s = new Scanner(new File("sqlcommands.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		s.useDelimiter(";");
		String SQLcreatetable = "";
		while (s.hasNext()) {
			SQLcreatetable = s.next();
			// System.out.println("out " + SQLcreatetable);
			stmt = null;
			try {
				stmt = database.getConnection().createStatement();
				stmt.executeUpdate(SQLcreatetable);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.toString());
				System.out.println(e.getMessage());
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}
		}
	}
}