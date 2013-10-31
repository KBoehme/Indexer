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

	Database database;
	/**
	 * 
	 */
	public ProjectDAO(Database database) {
		// TODO Auto-generated constructor stub
		this.database = database;
	}

	/**
	 * This function will return all of the current projects.
	 * 
	 * @return ArrayList<project> allprojects
	 * @throws SQLException
	 */
	public ArrayList<Project> getAll() throws SQLException {
		ArrayList<Project> allprojects = new ArrayList<Project>();
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM projects";
			stmt = database.getConnection().prepareStatement(sql);
			results = stmt.executeQuery(sql);
			while (results.next()) {
				// Extract all the information from the project we pulled.
				int id = results.getInt(1);
				String title = results.getString(2);
				int recordsperimage = results.getInt(3);
				int firstycoord = results.getInt(4);
				int recordheight = results.getInt(5);
				
				Project project = new Project(id,title, recordsperimage, firstycoord, recordheight);
				allprojects.add(project);
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
		return allprojects;
	}


	/** Query the database.
	 * 
	 */
	public void query(Project project) {
		
	}
	
	/** Insert into the database.
	 * @return 
	 * @throws SQLException 
	 * 
	 */
	public void insert(Project project) throws SQLException {
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;
		try {
			String addsql = "INSERT INTO projects (title, recordsperimage, firstycoord, recordheight) VALUES (?,?,?,?)";
			pstmt = con.prepareStatement(addsql);

			pstmt.setString(1, project.getTitle());
			pstmt.setInt(2, project.getRecordsperimage());
			pstmt.setInt(3, project.getFirstycoord());
			pstmt.setInt(4, project.getRecordheight());
			
			if(pstmt.executeUpdate() == 1) {
				stmt = con.createStatement();
				//results = stmt.executeQuery("SELECT last_insert.rowid()");
				//results.next();
				//int uid = results.getInt(1); // ID of the new user
				//project.setID(uid);
			} else {
				//ERROR :Q
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
	
	/** Update the databae.
	 * 
	 */
	public void update(Project project) {
		
	}
	
	/** Delete something from the database.
	 * 
	 */
	public void delete(Project project) {
		
	}
}
