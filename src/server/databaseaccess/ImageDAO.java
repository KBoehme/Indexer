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
		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM Images";
			pstmt = database.getConnection().prepareStatement(sql);
			
			results = pstmt.executeQuery();
			
			while (results.next()) {
				// Extract all the information from the image we pulled.
				int id = results.getInt(1);
				String url = results.getString(2);
				int projectid = results.getInt(4);
				boolean hasbeenindexed = results.getBoolean(3);
				
				Image image = new Image(id, url, projectid, hasbeenindexed);
				allimages.add(image);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (results != null)
				results.close();
			if (pstmt != null)
				pstmt.close();
		}
		return allimages;
	}

	public Image getImage(int projectid, Database database, boolean dlimage)
			throws SQLException {

		if (dlimage) {

			PreparedStatement pstmt = null;
			ResultSet results = null;
			Image image = null;

			try {
				String sql = "SELECT * FROM Images where projectID = ? AND hasbeenindexed = ?";
				pstmt = database.getConnection().prepareStatement(sql);

				pstmt.setInt(1, projectid);
				pstmt.setBoolean(2, false);

				results = pstmt.executeQuery();

				while (results.next()) {
					int id = results.getInt(1);
					String fileurl = results.getString(2);
					boolean hasbeenindexed = results.getBoolean(3);
					
					image = new Image(id, fileurl, projectid, hasbeenindexed);
					if (dlimage) {
						image.setHasbeenindexed(true);
						this.update(image, database);
						break;
					}
					break;
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
			return image;
		} else {
			PreparedStatement pstmt = null;
			ResultSet results = null;
			Image image = null;

			try {
				String sql = "SELECT * FROM Images where projectID = ?";
				pstmt = database.getConnection().prepareStatement(sql);

				pstmt.setInt(1, projectid);

				results = pstmt.executeQuery();

				while (results.next()) {
					// Extract all the information from the User we pulled.
					int id = results.getInt(1);
					String fileurl = results.getString(2);
					boolean hasbeenindexed = results.getBoolean(3);
					
					image = new Image(id, fileurl, projectid, hasbeenindexed);
					break;
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
			return image;
		}

	}
	
	public ArrayList<Image> getSearchImages(int get_projectid, Database database) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet results = null;
		ArrayList<Image> images = new ArrayList<Image>();

		try {
			String sql = "SELECT * FROM Images where projectid = ? AND hasbeenindexed = ?";
			pstmt = database.getConnection().prepareStatement(sql);

			pstmt.setInt(1, get_projectid);
			pstmt.setBoolean(2, true);

			results = pstmt.executeQuery();

			while (results.next()) {
				// Extract all the information from the User we pulled.
				int id = results.getInt(1);
				String fileurl = results.getString(2);
				boolean hasbeenindexed = results.getBoolean(3);
				int projectid = results.getInt(4);
				
				Image image = new Image(id, fileurl, projectid, hasbeenindexed);
				images.add(image);
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
		return images;
	}

	public Image getImage(int imageid, Database database) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet results = null;
		Image image = null;

		try {
			String sql = "SELECT * FROM Images where ID = ?";
			pstmt = database.getConnection().prepareStatement(sql);

			pstmt.setInt(1, imageid);

			results = pstmt.executeQuery();

			while (results.next()) {
				// Extract all the information from the User we pulled.
				int id = results.getInt(1);
				String fileurl = results.getString(2);
				boolean hasbeenindexed = results.getBoolean(3);
				int projectid = results.getInt(4);
				
				image = new Image(id, fileurl, projectid, hasbeenindexed);
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
		return image;
	}

	/**
	 * Insert image into the database.
	 * 
	 * @throws SQLException
	 * 
	 */
	public int insert(Image image, Database database) throws SQLException {
		int uid = 0;
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;
		try {
			String addsql = "INSERT INTO Images (file, projectID, hasbeenindexed) VALUES (?,?,?)";
			pstmt = con.prepareStatement(addsql);

			pstmt.setString(1, String.valueOf(image.getFileurl()));
			pstmt.setInt(2, image.getProjectID());
			pstmt.setBoolean(3, image.isHasbeenindexed());

			if (pstmt.executeUpdate() == 1) {
				stmt = con.createStatement();
				results = stmt.executeQuery("SELECT last_insert_rowid()");
				results.next();
				uid = results.getInt(1); // ID of the new user
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
		
		return uid;
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
			String addsql = "UPDATE images SET hasbeenindexed = ? WHERE ID = ?";
			pstmt = con.prepareStatement(addsql);
			
			
			pstmt.setBoolean(1, image.isHasbeenindexed());
			pstmt.setInt(2, image.getID());
			
			if (pstmt.executeUpdate() == 1) {
				// System.out.println("Success: image updated.");
			} else {
				// ERROR :Q
				// System.out.println("Failed: Unable to update image.");
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
		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "DELETE FROM Images WHERE id = " + image.getID();
			pstmt = con.prepareStatement(sql);
			if (pstmt.executeUpdate() == 1) {
				System.out.println("Success: image deleted from database.");
			} else {
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
	 * Delete all images from the database.
	 * 
	 * @throws SQLException
	 * 
	 */
	public void deleteAll(Database database) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "DROP TABLE IF EXISTS Images;";
			String sql2 = "CREATE TABLE Images(ID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,file VARCHAR NOT NULL UNIQUE ,	hasbeenindexed BOOL DEFAULT 0,projectID INTEGER);";		
			
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
