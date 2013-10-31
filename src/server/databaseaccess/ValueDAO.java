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
import shared.model.Value;

/**
 * <p>
 * This object performs specific operations on the server relating to Values. Such as add value,
 * value value, etc.
 * </p>
 * 
 * @author Kevin
 * 
 */
public class ValueDAO {

	Database database;

	/**
	 *  Default Constructor
	 */
	public ValueDAO(Database database) {
		// TODO Auto-generated constructor stub
		this.database = database;
	}

	/**
	 * This function will return all of the current values.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Value> getAll() throws SQLException {
		ArrayList<Value> allvalues = new ArrayList<Value>();
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM values";
			stmt = database.getConnection().prepareStatement(sql);
			results = stmt.executeQuery(sql);
			while (results.next()) {
				// Extract all the information from the value we pulled.
				int id = results.getInt(1);
				String valuestring = results.getString(2);
				int recordID = results.getInt(3);
				
				Value value = new Value(id, valuestring, recordID);
				allvalues.add(value);
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
		return allvalues;
	}
	
	/**
	 * Insert value into the database.
	 * 
	 * @throws SQLException
	 */
	public void insert(Value value) throws SQLException {

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "INSERT INTO values (value, recordID) VALUES (?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, value.getValue());
			pstmt.setInt(2, value.getRecordID());

			if (pstmt.executeUpdate() == 1) {
				System.out.println("Success: value inserted into database.");
				stmt = con.createStatement();
				// results = stmt.executeQuery("SELECT last_insert.rowid()");
				// results.next();
				// int uid = results.getInt(1); // ID of the new value
				// value.setID(uid);
			} else {
				System.out.println("Failed: Unable to insert value into database.");
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
	 * Update value in the database.
	 * 
	 * @param value
	 * @throws SQLException
	 */
	public void update(Value value) throws SQLException {

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String addsql = "UPDATE values SET (value, password) VALUES (?,?)";
			pstmt = con.prepareStatement(addsql);

			pstmt.setString(1, value.getValue());
			pstmt.setInt(2, value.getRecordID());

			if (pstmt.executeUpdate() == 1) {
				System.out.println("Success: value updated.");
			} else {
				// ERROR :Q
				System.out.println("Failed: Unable to update value.");
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
	 * @param value
	 */
	public void query(Value value) {
		// Query looks for information given a values input
		Connection con = database.getConnection();
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT FROM Values WHERE Values.ID =" + value.getID();
			stmt = con.prepareStatement(sql);
			stmt.executeQuery(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Delete value from the database.
	 * 
	 * @throws SQLException
	 */
	public void delete(Value value) throws SQLException {

		Connection con = database.getConnection();
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "DELETE FROM Value WHERE id = " + value.getID();
			stmt = con.prepareStatement(sql);
			if (stmt.executeUpdate(sql) == 1) {
				System.out.println("Success: value deleted from database.");
			} else {
				System.out.println("Failed: Unable to delete value from database.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (results != null)
				results.close();
		}

	}
}
