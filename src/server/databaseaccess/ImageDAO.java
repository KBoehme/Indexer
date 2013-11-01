/**
 * 
 */
package server.databaseaccess;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.database.Database;
import shared.model.Image;
import shared.model.User;

/**
 * @author Kevin
 * 
 */
public class ImageDAO {

	/**
	 * 
	 */
	public ImageDAO() {

	}

	/**
	 * Query the database.
	 * 
	 */
	public void query(Image image) {

	}

	public ArrayList<Image> getAllImages(Database database)
			throws MalformedURLException, SQLException {
		ArrayList<Image> allimages = new ArrayList<Image>();
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM images";
			stmt = database.getConnection().prepareStatement(sql);
			results = stmt.executeQuery(sql);
			while (results.next()) {
				// Extract all the information from the image we pulled.
				int id = results.getInt(1);
				String url = results.getString(2);
				int projectid = results.getInt(3);

				Image image = new Image(id, url, projectid);
				allimages.add(image);
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
		return allimages;
	}

	public Image getImage(int projectid, Database database) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet results = null;
		Image image = null;

		try {
			String sql = "SELECT * FROM Images WHERE projectid = ?";
			pstmt = database.getConnection().prepareStatement(sql);

			pstmt.setInt(1, projectid);

			results = pstmt.executeQuery();

			results.next();
			// Extract all the information from the User we pulled.
			int id = results.getInt(1);
			String fileurl = results.getString(2);
			int project_id = results.getInt(3);
			
			image = new Image(id, fileurl, project_id);
			image.setHasbeenindexed(true);

			this.update(image, database);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null)
				results.close();
			if (pstmt != null)
				pstmt.close();
		}
		return image;
	}

	/**
	 * Insert image into the database.
	 * 
	 * @throws SQLException
	 * 
	 */
	public void insert(Image image, Database database) throws SQLException {
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;
		try {
			String addsql = "INSERT INTO Images (file, projectID) VALUES (?,?)";
			pstmt = con.prepareStatement(addsql);

			pstmt.setString(1, String.valueOf(image.getFileurl()));
			pstmt.setInt(2, image.getProjectID());

			if (pstmt.executeUpdate() == 1) {
				stmt = con.createStatement();
				// results = stmt.executeQuery("SELECT last_insert.rowid()");
				// results.next();
				// int uid = results.getInt(1); // ID of the new user
				// project.setID(uid);
			} else {
				// ERROR :Q
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
	 * Update image in the databae.
	 * 
	 * @throws SQLException
	 */
	public void update(Image image, Database database) throws SQLException {

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String addsql = "UPDATE images SET ID = ?, file = ?, hasbeenindexed = ?, projectID = ? WHERE ID = ?";
			
			//System.out.println("image file: " + image.getFileurl());
			//System.out.println("bool : " + image.isHasbeenindexed());
			pstmt = con.prepareStatement(addsql);
			pstmt.setInt(1, image.getID());
			pstmt.setString(2, image.getFileurl());
			pstmt.setBoolean(3, image.isHasbeenindexed());
			pstmt.setInt(4, image.getProjectID());
			pstmt.setInt(5, image.getID());


			if (pstmt.executeUpdate() == 1) {
				//System.out.println("Success: image updated.");
			} else {
				// ERROR :Q
				//System.out.println("Failed: Unable to update image.");
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
	 * Delete image from the database.
	 * 
	 * @throws SQLException
	 * 
	 */
	public void delete(Image image, Database database) throws SQLException {

		Connection con = database.getConnection();
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "DELETE FROM image WHERE id = " + image.getID();
			stmt = con.prepareStatement(sql);
			if (stmt.executeUpdate(sql) == 1) {
				System.out.println("Success: image deleted from database.");
			} else {
				System.out
						.println("Failed: Unable to delete image from database.");
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
