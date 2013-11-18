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
import shared.model.Image;
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

	/**
	 *  Default Constructor
	 */
	public ValueDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This function will return all of the current values.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Value> getAll(Database database) throws SQLException {
		ArrayList<Value> allvalues = new ArrayList<Value>();
		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM Value";
			pstmt = database.getConnection().prepareStatement(sql);
			results = pstmt.executeQuery();
			
			while (results.next()) {
				// Extract all the information from the value we pulled.
				int id = results.getInt(1);
				String valuestring = results.getString(2);
				int fieldnum = results.getInt(3);
				int rownum = results.getInt(4);
				int imageID = results.getInt(5);
				int recordID = results.getInt(6);
				
				
				Value value = new Value(id, valuestring, rownum, fieldnum, imageID, recordID);
				allvalues.add(value);
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
		return allvalues;
	}
	
	public ArrayList<Value> getValues(int imageid, int rownum, Database database) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet results = null;
		ArrayList<Value> values = new ArrayList<Value>();

		try {
			String sql = "SELECT * FROM Value where fieldnum = ? AND imageid = ?";
			pstmt = database.getConnection().prepareStatement(sql);
			
			pstmt.setInt(1, rownum);
			pstmt.setInt(2, imageid);

			results = pstmt.executeQuery();

			while (results.next()) {
				// Extract all the information from the User we pulled.
				int id = results.getInt(1);
				String string_value = results.getString(2);
				int fieldnum = results.getInt(3);
				int rownumb = results.getInt(4);
				int val_imageid = results.getInt(5);
				int recordid = results.getInt(6);
				
				Value value = new Value(id, string_value, fieldnum, rownumb, val_imageid, recordid);
				values.add(value);
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
		return values;
	}
	
	/**
	 * Insert value into the database.
	 * 
	 * @throws SQLException
	 */
	public void insert(Value value, Database database) throws SQLException {

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		ResultSet results = null;

		
		try {
			String sql = "INSERT INTO value (value, fieldnum, rownum, imageID, recordID) VALUES (?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, value.getValue());
			pstmt.setInt(2, value.getFieldnum());
			pstmt.setInt(3, value.getRownum());
			pstmt.setInt(4, value.getImageID());
			pstmt.setInt(5, value.getRecordID());

			pstmt.executeUpdate();

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
	 * Update value in the database.
	 * 
	 * @param value
	 * @throws SQLException
	 */
	public void update(Value value, Database database) throws SQLException {

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
	public void query(Value value, Database database) {
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
	public void delete(Value value, Database database) throws SQLException {

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
	
	/**
	 * Delete all values from the database.
	 * 
	 * @throws SQLException
	 * 
	 */
	public void deleteAll(Database database) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "DROP TABLE IF EXISTS Value;";
			String sql2 = "CREATE TABLE Value (ID INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL ,value VARCHAR NOT NULL ,fieldnum INTEGER ,rownum INTGER ,imageID INTEGER ,recordID INTEGER );";		
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
