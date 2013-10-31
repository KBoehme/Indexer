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
import shared.model.User;

/**
 * <p>
 * This object performs specific operations on the server relating to Users. Such as add user,
 * delete user, etc.
 * </p>
 * 
 * @author Kevin
 * 
 */
public class UserDAO {

	// private static Logger logger;

	Database database;

	/**
	 * Default Constructor
	 */
	public UserDAO(Database database) {
		// TODO Auto-generated constructor stub
		this.database = database;
	}

	/**
	 * This function will return all of the current Users.
	 * 
	 * @return ArrayList<User> allusers
	 * @throws SQLException
	 */
	public ArrayList<User> getAll() throws SQLException {
		ArrayList<User> allUsers = new ArrayList<User>();
		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "SELECT * FROM Users";
			stmt = database.getConnection().prepareStatement(sql);
			results = stmt.executeQuery(sql);
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
				User user = new User(username, password, firstname, lastname, email, num_indexed_records,
						current_batch_id);
				allUsers.add(user);
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
		return allUsers;
	}

	public User getUser(String get_username, String get_password) throws SQLException {

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		ResultSet results = null;
		User user = null;

		try {
			String sql = "SELECT * FROM Users WHERE username = ? , password = ? ";

			pstmt.setString(1, get_username);
			pstmt.setString(2, get_password);

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

				user = new User(username, password, firstname, lastname, email, num_indexed_records, current_batch_id);
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

		return user;
	}

	/**
	 * Insert user into the database.
	 * 
	 * @throws SQLException
	 */
	public void insert(User user) throws SQLException {

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "INSERT INTO users (username, password, firstname, lastname, email, num_indexed_records, current_batch_id) VALUES (?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getFirstname());
			pstmt.setString(4, user.getLastname());
			pstmt.setString(5, user.getEmail());
			pstmt.setLong(6, user.getNum_indexed_records());
			pstmt.setLong(7, user.getCurr_batch_id());

			if (pstmt.executeUpdate() == 1) {
				// System.out.println("Success: User inserted into database.");
				stmt = con.createStatement();
				// results = stmt.executeQuery("SELECT last_insert.rowid()");
				// results.next();
				// int uid = results.getInt(1); // ID of the new user
				// user.setID(uid);
			} else {
				System.out.println("Failed: Unable to insert user into database.");
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
	 * Update user in the database.
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void update(User user) throws SQLException {

		Connection con = database.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet results = null;

		try {
			String addsql = "UPDATE Users SET (username, password, firstname, lastname, email, num_indexed_records, current_batch_id) VALUES (?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(addsql);

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getFirstname());
			pstmt.setString(4, user.getLastname());
			pstmt.setString(5, user.getEmail());
			pstmt.setLong(6, user.getNum_indexed_records());
			pstmt.setLong(7, user.getCurr_batch_id());

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
	 * @param user
	 */
	public void query(User user) {
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
	public void delete(User user) throws SQLException {

		Connection con = database.getConnection();
		Statement stmt = null;
		ResultSet results = null;

		try {
			String sql = "DELETE FROM User WHERE id = " + user.getID();
			stmt = con.prepareStatement(sql);
			if (stmt.executeUpdate(sql) == 1) {
				System.out.println("Success: User deleted from database.");
			} else {
				System.out.println("Failed: Unable to delete user from database.");
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