/**
 * 
 */
package client.communicator;

import shared.model.User;

/**
 * @author Kevin
 *
 */
public class ValidateUser_param {

	private String username;
	private String password;
	
	private User user;

	/**
	 * 
	 */
	public ValidateUser_param() {
		// TODO Auto-generated constructor stub
		user = null;
	}
	
	public ValidateUser_param(String username, String password) {
		// TODO Auto-generated constructor stub
		this.username = username;
		this.username = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
