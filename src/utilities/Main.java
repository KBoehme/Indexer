/**
 * 
 */
package utilities;

import server.database.Database;

/**
 * @author kboehme1
 *
 */
public class Main {

	/**
	 * @param full
	 *            path to XML file
	 */
	public static void main(String[] args) {
		System.out.println("Data Importer.");
		try {
			// This standalone program takes in one argument which is the
			// relative or absolute path to the XML file.
			String pathtoxml = args[0];

			// First delete all tables from SQLite database and directory
			// structure

			Database database = new Database();
			Database.initialize();
			database.startTransaction();
			
			DataImporter di = new DataImporter(pathtoxml, database);
			database.endTransaction(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("USAGE: java DataImporter [path_to_xml_file]");
		}
	}

}
