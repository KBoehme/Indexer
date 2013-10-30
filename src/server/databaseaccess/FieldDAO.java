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
import shared.model.Field;

/** 
 * @author Kevin
 *
 */
public class FieldDAO {

	Database database;
	/**
	 * 
	 */
	public FieldDAO(Database database) {
		// TODO Auto-generated constructor stub
		this.database = database;
	}
	
	/**
	 * Insert field into the database.
	 * 
	 * @throws SQLException
	 */
	public void insert(Field field) throws SQLException {

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "INSERT INTO Fields (title, xcoor, width, helphtml, knowndata, field_number, projectID) VALUES (?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, field.getTitle());
			pstmt.setInt(2, field.getX_cor());
			pstmt.setInt(3, field.getWidth());
			pstmt.setString(4, field.getHelphtml());
			pstmt.setString(5, field.getKnowndata());
			pstmt.setInt(6, field.getField_number());
			pstmt.setInt(7, field.getProjectID());

			if (pstmt.executeUpdate() == 1) {
				//System.out.println("Success: field inserted into database.");
				stmt = con.createStatement();
				// results = stmt.executeQuery("SELECT last_insert.rowid()");
				// results.next();
				// int uid = results.getInt(1); // ID of the new field
				// field.setID(uid);
			} else {
				System.out.println("Failed: Unable to insert field into database.");
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
	 * Update field in the database.
	 * 
	 * @param field
	 * @throws SQLException
	 */
	public void update(Field field) throws SQLException {

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String addsql = "UPDATE fields SET (title, xcoor, width, helphtml, knowndata, field_number, projectID) fieldS (?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(addsql);

			pstmt.setString(1, field.getTitle());
			pstmt.setInt(2, field.getX_cor());
			pstmt.setInt(3, field.getWidth());
			pstmt.setString(4, field.getHelphtml());
			pstmt.setString(5, field.getKnowndata());
			pstmt.setInt(6, field.getField_number());
			pstmt.setInt(7, field.getProjectID());

			if (pstmt.executeUpdate() == 1) {
				System.out.println("Success: field updated.");
			} else {
				// ERROR :Q
				System.out.println("Failed: Unable to update field.");
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
	 * @param field
	 */
	public void query(Field field) {
		// Query looks for information given a fields input
		Connection con = database.getConnection();
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT FROM fields WHERE fields.ID =" + field.getID();
			stmt = con.prepareStatement(sql);
			stmt.executeQuery(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Delete field from the database.
	 * 
	 * @throws SQLException
	 */
	public void delete(Field field) throws SQLException {

		Connection con = database.getConnection();
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "DELETE FROM field WHERE id = " + field.getID();
			stmt = con.prepareStatement(sql);
			if (stmt.executeUpdate(sql) == 1) {
				System.out.println("Success: field deleted from database.");
			} else {
				System.out.println("Failed: Unable to delete field from database.");
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
	 * This function will return all of the current fields.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Field> getAll() throws SQLException {
		ArrayList<Field> allfields = new ArrayList<Field>();
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM fields";
			stmt = database.getConnection().prepareStatement(sql);
			results = stmt.executeQuery(sql);
			while (results.next()) {
				// Extract all the information from the field we pulled.
				int id = results.getInt(1);
				String title = results.getString(2);
				int xcoor = results.getInt(3);
				int width = results.getInt(4);
				String helphtml = results.getString(5);
				String knowndata = results.getString(6);
				int field_number = results.getInt(7);
				int projectID = results.getInt(8);
				
				Field field = new Field(id, title, xcoor, width, helphtml, knowndata, field_number, projectID);
				allfields.add(field);
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
		return allfields;
	}

}
