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
import shared.communication.ValidateUser_result;
import shared.model.User;

/**
 * <p>
 * This object performs specific operations on the server relating to Users.
 * Such as add user, delete user, etc.
 * </p>
 * 
 * @author Kevin
 * 
 */
public class UserDAO {

	// private static Logger logger;

	/**
	 * Default Constructor
	 */
	public UserDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This function will return all of the current Users.
	 * 
	 * @return ArrayList<User> allusers
	 * @throws SQLException
	 */
	public ArrayList<User> getAll(Database database) throws SQLException {
		
		ArrayList<User> allUsers = new ArrayList<User>();
		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM Users";
			pstmt = database.getConnection().prepareStatement(sql);
			
			results = pstmt.executeQuery();

			while (results.next()) {
				// Extract all the information from the User we pulled.
				int id = results.getInt(1);
				String username = results.getString(2);
				String password = results.getString(3);
				String firstname = results.getString(4);
				String lastname = results.getString(5);
				String email = results.getString(6);
				int num_indexed_records = results.getInt(7);
				int current_batch_id = results.getInt(8);
				User user = new User(username, password, firstname, lastname,
						email, num_indexed_records, current_batch_id);
				allUsers.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (results != null)
				results.close();
			if (pstmt != null)
				pstmt.close();
		}
		return allUsers;
	}

	public User getUser(String get_username, String get_password,
			Database database) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet results = null;
		User user = null;

		try {
			String sql = "SELECT * FROM Users WHERE username = ?";
			pstmt = database.getConnection().prepareStatement(sql);

			pstmt.setString(1, get_username);

			results = pstmt.executeQuery();

			while (results.next()) {
				if (get_password.equals(results.getString(3))) {
					// Extract all the information from the User we pulled.
					int id = results.getInt(1);
					String username = results.getString(2);
					String password = results.getString(3);
					String firstname = results.getString(4);
					String lastname = results.getString(5);
					String email = results.getString(6);
					int num_indexed_records = results.getInt(7);
					int current_batch_id = results.getInt(8);

					user = new User(username, password, firstname, lastname,
							email, num_indexed_records, current_batch_id);
					user.setID(id);
				}
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

		// We will either return the User with info or a null user if nothing
		// matched...
		return user;
	}

	/**
	 * Insert user into the database.
	 * 
	 * @throws SQLException
	 */
	public void insert(User user, Database database) throws SQLException {

		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "INSERT INTO Users (username, password, firstname, lastname, email, num_indexed_records, current_batch_id) VALUES (?,?,?,?,?,?,?)";
			pstmt = database.getConnection().prepareStatement(sql);

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getFirstname());
			pstmt.setString(4, user.getLastname());
			pstmt.setString(5, user.getEmail());
			pstmt.setLong(6, user.getNum_indexed_records());
			pstmt.setLong(7, user.getCurr_batch_id());

			if (pstmt.executeUpdate() == 1) {
			} else {
				System.out
						.println("Failed: Unable to insert user into database.");
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
	 * Update user in the database.
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void update(User user, Database database) throws SQLException {

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String addsql = "UPDATE Users SET num_indexed_records = ? , current_batch_id = ? WHERE ID = ?";
			pstmt = con.prepareStatement(addsql);

			pstmt.setInt(1, user.getNum_indexed_records());
			pstmt.setInt(2, user.getCurr_batch_id());
			pstmt.setInt(3, user.getID());

			if (pstmt.executeUpdate() == 1) {
				System.out.println("Success: User updated.");
			} else {
				// ERROR :Q
				System.out.println("Failed: Unable to update user.");
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
	 * @param user
	 */
	public void query(User user, Database database) {
		// Query looks for information given a users input
		Connection con = database.getConnection();
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT FROM users WHERE users.ID =" + user.getID();
			stmt = con.prepareStatement(sql);
			stmt.executeQuery(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Delete user from the database.
	 * 
	 * @throws SQLException
	 * 
	 */
	public void delete(User user, Database database) throws SQLException {

		Connection con = database.getConnection();
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "DELETE FROM User WHERE id = " + user.getID();
			stmt = con.prepareStatement(sql);
			if (stmt.executeUpdate(sql) == 1) {
				System.out.println("Success: User deleted from database.");
			} else {
				System.out
						.println("Failed: Unable to delete user from database.");
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
	 * Delete all user from the database.
	 * 
	 * @throws SQLException
	 * 
	 */
	public void deleteAll(Database database) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet results = null;

		try {
			String sql = "DROP TABLE IF EXISTS Users;";
			String sql2 = "CREATE TABLE Users(ID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , username VARCHAR NOT NULL  UNIQUE , password VARCHAR NOT NULL , firstname VARCHAR NOT NULL , lastname VARCHAR NOT NULL , email VARCHAR UNIQUE , num_indexed_records INTEGER, current_batch_id INTEGER DEFAULT -1);";
			
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