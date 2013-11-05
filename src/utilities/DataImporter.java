package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import server.database.Database;

/**
 * This class reads in the Records file and copies all data over to secure
 * location. It also calls the parseXML class.
 * 
 * @author Kevin
 * 
 */
public class DataImporter {

	/**
	 * Default Constructor
	 * 
	 */
	public DataImporter(String pathtoxml, Database database) {
		// TODO Auto-generated constructor stub
		try {
			this.createAllTables(database);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		XMLParser xmlparser = new XMLParser(pathtoxml, database);
	}

	public void createAllTables(Database database) throws SQLException {
		// TODO : figure out how to use this stuff??

		//System.out.println("create all tables");

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