/**
 * 
 */
package server.databaseaccess;

import java.sql.SQLException;
import java.sql.Statement;

import server.database.Database;
import shared.model.Record;

/**
 * @author Kevin
 *
 */
public class RecordDAO {

	Database database;
	/**
	 * 
	 */
	public RecordDAO(Database database) {
		// TODO Auto-generated constructor stub
		this.database = database;
	}

	/** Query the database.
	 * 
	 *
	 * @param record
	 */
	public void query(Record record) {
		
	}
	
	/** Insert into the database.
	 * 
	 *
	 * @param record
	 */
	public void insert(Record record) {
		
	}
	
	/** Update the databae.
	 * 
	 * 
	 * @param record
	 */
	public void update(Record record) {
		
	}
	
	/** Delete something from the database.
	 * 
	 * @param record
	 */
	public void delete(Record record) {
		
	}
	
}
