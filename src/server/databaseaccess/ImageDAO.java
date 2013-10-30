/**
 * 
 */
package server.databaseaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.database.Database;
import shared.model.Image;

/**
 * @author Kevin
 *
 */
public class ImageDAO {

	Database database;
	/**
	 * 
	 */
	public ImageDAO(Database database) {
		// TODO Auto-generated constructor stub
		this.database = database;
	}
	
	/** Query the database.
	 * 
	 */
	public void query(Image image) {
		
	}
	
	/** Insert into the database.
	 * @throws SQLException 
	 * 
	 */
	public void insert(Image image) throws SQLException {
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;
		try {
			String addsql = "INSERT INTO Images (file, projectID) VALUES (?,?)";
			pstmt = con.prepareStatement(addsql);

			pstmt.setString(1, String.valueOf(image.getFileurl()));
			pstmt.setInt(2, image.getProjectID());
			
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
	public void update(Image image) {
		
	}
	
	/** Delete something from the database.
	 * 
	 */
	public void delete(Image image) {
		
	}
	
}
