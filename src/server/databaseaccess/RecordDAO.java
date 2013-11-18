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

	/**
	 * 
	 */
	public RecordDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This function will return all of the current records.
	 * 
	 * @return ArrayList<record> allrecords
	 * @throws SQLException
	 */
	public ArrayList<Record> getAll(Database database) throws SQLException {

		ArrayList<Record> allrecords = new ArrayList<Record>();

		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM records";
			pstmt = database.getConnection().prepareStatement(sql);
			
			
			results = pstmt.executeQuery();
			while (results.next()) {
				//TODO : get the record information here.
				Record record = new Record();
				allrecords.add(record);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (results != null)
				results.close();
			if (pstmt != null)
				pstmt.close();
		}
		return allrecords;
	}
	
	public int getRecordRowNumber(int recordid, Database database) throws SQLException {

		int row_number = -1;

		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM records WHERE ID = ?";
			pstmt = database.getConnection().prepareStatement(sql);
			
			pstmt.setInt(1, recordid);
			
			results = pstmt.executeQuery();
			while (results.next()) {
				row_number = results.getInt(2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null)
				results.close();
			if (pstmt != null)
				pstmt.close();
		}
		return row_number;
	}

	/**
	 * Insert record into the database.
	 * 
	 * @throws SQLException
	 * @return int (the record id number)
	 */
	public int insert(Record record, Database database) throws SQLException {

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		int uid = 0;

		try {
			String sql = "INSERT INTO records (rownumber, imageID, hasvalues) VALUES (?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, record.getRownumber());
			pstmt.setInt(2, record.getImageID());
			pstmt.setBoolean(3, record.isHasvalues());

			// System.out.println(pstmt.toString());
			if (pstmt.executeUpdate() == 1) {
				stmt = con.createStatement();
				results = stmt.executeQuery("SELECT last_insert_rowid()");
				results.next();
				uid = results.getInt(1); // ID of the new record
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

		return uid;
	}

	/**
	 * Update record in the database.
	 * 
	 * @param record
	 * @throws SQLException
	 */
	public void update(Record record, Database database) throws SQLException {

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String addsql = "UPDATE records SET (rownumber, projectID, imageID, hasvalues) VALUES (?,?,?,?)";
			pstmt = con.prepareStatement(addsql);

			pstmt.setInt(1, record.getRownumber());
			pstmt.setInt(3, record.getImageID());
			pstmt.setBoolean(4, record.isHasvalues());

			if (pstmt.executeUpdate() == 1) {
			} else {
				// ERROR :Q
				System.out.println("Failed: Unable to update record.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	 * Query the database.
	 * 
	 * @param record
	 */
	public void query(Record record, Database database) {
		// Query looks for information given a records input
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT FROM records WHERE (rownumber, projectID, imageID) VALUES (?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, record.getRownumber());
			pstmt.setInt(3, record.getImageID());

			pstmt.executeQuery(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Delete record from the database.
	 * 
	 * @throws SQLException
	 * 
	 */
	public void delete(Record record, Database database) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "DELETE FROM Records WHERE rownumber=? AND imageid=?";
			pstmt = database.getConnection().prepareStatement(sql);

			pstmt.setInt(1, record.getRownumber());
			pstmt.setInt(2, record.getImageID());

			if (pstmt.executeUpdate() == 1) {
			} else {
				System.out
						.println("Failed: Unable to delete record from database.");
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
	
	/**
	 * Delete all records from the database.
	 * 
	 * @throws SQLException
	 * 
	 */
	public void deleteAll(Database database) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "DROP TABLE IF EXISTS Records;";
			String sql2 = "CREATE TABLE Records(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,rownumber INTEGER NOT NULL ,imageID INTEGER ,hasvalues BOOL DEFAULT 0);";
			
			pstmt = database.getConnection().prepareStatement(sql);
			pstmt.execute();
			
			pstmt = database.getConnection().prepareStatement(sql2);
			pstmt.execute();
			
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
