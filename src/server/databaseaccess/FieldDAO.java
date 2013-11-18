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

	/**
	 * 
	 */
	public FieldDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Insert field into the database.
	 * 
	 * @throws SQLException
	 */
	public void insert(Field field, Database database) throws SQLException {

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
	public void update(Field field, Database database) throws SQLException {

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
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
				pstmt.close();
			if (results != null)
				results.close();
		}
	}

	/**
	 * Query the database.
	 * 
	 * @param field
	 */
	public void query(Field field, Database database) {
		// Query looks for information given a fields input
		Connection con = database.getConnection();
		Statement stmt = null;

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
	public void delete(Field field, Database database) throws SQLException {

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
	 * This function will return all of the fields associated with a given projectid.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Field> getFields(int projectid, Database database) throws SQLException {
		ArrayList<Field> fields = new ArrayList<Field>();

		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM fields WHERE projectid = ?";
			pstmt = database.getConnection().prepareStatement(sql);

			pstmt.setInt(1, projectid);

			results = pstmt.executeQuery();

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
				fields.add(field);
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
		return fields;
	}

	/**
	 * This function will return all of the current fields.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Field getField(int fieldid, Database database) throws SQLException {
		Field field = null;

		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM fields WHERE ID = ?";
			pstmt = database.getConnection().prepareStatement(sql);

			pstmt.setInt(1, fieldid);

			results = pstmt.executeQuery();

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

				field = new Field(id, title, xcoor, width, helphtml, knowndata, field_number, projectID);
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
		return field;
	}

	/**
	 * This function will return all of the current fields.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Field> getAll(Database database) throws SQLException {
		ArrayList<Field> allfields = new ArrayList<Field>();
		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM fields";
			pstmt = database.getConnection().prepareStatement(sql);

			results = pstmt.executeQuery();

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
			e.printStackTrace();
		} finally {
			if (results != null)
				results.close();
			if (pstmt != null)
				pstmt.close();
		}
		return allfields;
	}
	
	/**
	 * Delete all fields from the database.
	 * 
	 * @throws SQLException
	 * 
	 */
	public void deleteAll(Database database) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "DROP TABLE IF EXISTS Fields;";
			String sql2 = "CREATE TABLE Fields (ID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , title VARCHAR NOT NULL ,xcoor INTEGER NOT NULL , width INTEGER,helphtml VARCHAR ,knowndata VARCHAR, field_number INTEGER NOT NULL ,projectID INTEGER NOT NULL );";
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
