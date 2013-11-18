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
import shared.model.Project;
import shared.model.User;

/**
 * @author Kevin
 * 
 */
public class ProjectDAO {

	/**
	 * 
	 */
	public ProjectDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This function will return all of the current projects.
	 * 
	 * @return ArrayList<project> allprojects
	 * @throws SQLException
	 */
	public ArrayList<Project> getAll(Database database) throws SQLException {
		ArrayList<Project> allprojects = new ArrayList<Project>();

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM projects";
			pstmt = database.getConnection().prepareStatement(sql);

			results = pstmt.executeQuery();

			while (results.next()) {
				// Extract all the information from the project we pulled.
				int id = results.getInt(1);
				String title = results.getString(2);
				int recordsperimage = results.getInt(3);
				int numfields = results.getInt(4);
				int firstycoord = results.getInt(5);
				int recordheight = results.getInt(6);

				Project project = new Project(id, title, recordsperimage,
						numfields, firstycoord, recordheight);
				allprojects.add(project);
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
		return allprojects;
	}

	public Project getProject(int projectid, Database database)
			throws SQLException {
		Project project = null;

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM projects WHERE ID = ?";
			pstmt = database.getConnection().prepareStatement(sql);

			pstmt.setInt(1, projectid);

			results = pstmt.executeQuery();

			while (results.next()) {
				// Extract all the information from the project we pulled.
				int id = results.getInt(1);
				String title = results.getString(2);
				int recordsperimage = results.getInt(3);
				int numfields = results.getInt(4);
				int firstycoord = results.getInt(5);
				int recordheight = results.getInt(6);

				project = new Project(id, title, recordsperimage, numfields,
						firstycoord, recordheight);
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
		return project;
	}

	/**
	 * Query the database.
	 * 
	 */
	public void query(Project project, Database database) {

	}

	/**
	 * Insert into the database.
	 * 
	 * @return
	 * @throws SQLException
	 * 
	 */
	public void insert(Project project, Database database) throws SQLException {
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;
		try {
			String addsql = "INSERT INTO projects (title, recordsperimage, numfields, firstycoord, recordheight) VALUES (?,?,?,?,?)";
			pstmt = con.prepareStatement(addsql);

			pstmt.setString(1, project.getTitle());
			pstmt.setInt(2, project.getRecordsperimage());
			pstmt.setInt(3, project.getNumfields());
			pstmt.setInt(4, project.getFirstycoord());
			pstmt.setInt(5, project.getRecordheight());
			pstmt.executeUpdate();

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
	 * Update the databae.
	 * 
	 */
	public void update(Project project, Database database) {
		// TODO: update function

	}

	/**
	 * Delete something from the database.
	 * 
	 */
	public void delete(Project project, Database database) {
		// TODO: delete function
	}

	/**
	 * Delete all projects from the database.
	 * 
	 * @throws SQLException
	 * 
	 */
	public void deleteAll(Database database) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "DROP TABLE IF EXISTS Projects;";
			String sql2 = "CREATE TABLE Projects(ID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , title VARCHAR NOT NULL UNIQUE ,recordsperimage INTEGER ,numfields INTEGER , firstycoord INTEGER NOT NULL ,recordheight INTEGER NOT NULL );";
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
