/**
 * 
 */
package server.databaseaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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



	/**
	 * This function will return all of the current records.
	 * @return ArrayList<record> allrecords
	 * @throws SQLException
	 */
	public ArrayList<Record> getAll() throws SQLException {
		
		ArrayList<Record> allrecords = new ArrayList<Record>();
		
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM records";
			stmt = database.getConnection().prepareStatement(sql);
			results = stmt.executeQuery(sql);
			while (results.next()) {
				// Extract all the information from the record we pulled.
//				int id = results.getInt(1);
//				String recordname = results.getString(2);
//				String password = results.getString(3);
//				String firstname = results.getString(4);
//				String lastname = results.getString(5);
//				String email = results.getString(6);
//				int num_indexed_records = results.getInt(7);
//				int current_batch_id = results.getInt(8);
//				Record record = new Record(-1, );
//				allrecords.add(record);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null)
				results.close();
			if (stmt != null)
				stmt.close();
		}
		return allrecords;
	}
	
	/**
	 * Insert record into the database.
	 * @throws SQLException
	 */
	public void insert(Record record) throws SQLException {

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "INSERT INTO records (rownumber, projectID, imageID, hasvalues) VALUES (?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, record.getRownumber());
			pstmt.setInt(2, record.getProjectID());
			pstmt.setInt(3, record.getImageID());
			pstmt.setBoolean(4, record.isHasvalues());


			if (pstmt.executeUpdate() == 1) {
				//System.out.println("Success: record inserted into database.");
				stmt = con.createStatement();
				// results = stmt.executeQuery("SELECT last_insert.rowid()");
				// results.next();
				// int uid = results.getInt(1); // ID of the new record
				// record.setID(uid);
			} else {
				System.out.println("Failed: Unable to insert record into database.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				stmt.close();
			if (results != null)
				results.close();
			if (stmt != null)
				stmt.close();
		}
	}

	/**
	 * Update record in the database.
	 * @param record
	 * @throws SQLException 
	 */
	public void update(Record record) throws SQLException {
		
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;
		
		try {
			String addsql = "UPDATE records SET (rownumber, projectID, imageID, hasvalues) VALUES (?,?,?,?)";
			pstmt = con.prepareStatement(addsql);

			pstmt.setInt(1, record.getRownumber());
			pstmt.setInt(2, record.getProjectID());
			pstmt.setInt(3, record.getImageID());
			pstmt.setBoolean(4, record.isHasvalues());

			if(pstmt.executeUpdate() == 1) {
				System.out.println("Success: record updated.");
			} else {
				//ERROR :Q
				System.out.println("Failed: Unable to update record.");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) stmt.close();
			if (results != null) results.close();
			if (stmt != null) stmt.close();
		}
	}
	
	/**
	 * Query the database.
	 * @param record
	 */
	public void query(Record record) {
		// Query looks for information given a records input
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		ResultSet results = null;
		
		try {
			String sql = "SELECT FROM records WHERE (rownumber, projectID, imageID) VALUES (?,?,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, record.getRownumber());
			pstmt.setInt(2, record.getProjectID());
			pstmt.setInt(3, record.getImageID());
			
			pstmt.executeQuery(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Delete record from the database.
	 * @throws SQLException 
	 * 
	 */
	public void delete(Record record) throws SQLException {
		
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		ResultSet results = null;
		
		try {
			String sql = "DELETE FROM record WHERE (rownumber, projectID, imageID) VALUES (?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, record.getRownumber());
			pstmt.setInt(2, record.getProjectID());
			pstmt.setInt(3, record.getImageID());
			
			if (pstmt.executeUpdate(sql) == 1) {
				System.out.println("Success: record deleted from database.");
			} else {
				System.out.println("Failed: Unable to delete record from database.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (results != null)
				results.close();
		}

	}
}
