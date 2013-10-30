/**
 * 
 */
package client.communicator;

import java.util.ArrayList;

/**
 * @author Kevin
 *
 */
public class Search_param {

	private String username;
	private String password;
	private ArrayList<Integer> fields;
	private ArrayList<String> search_values;
	
	/**
	 * 
	 */
	public Search_param() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param username
	 * @param password
	 * @param fields
	 * @param search_values
	 */
	public Search_param(String username, String password,
			ArrayList<Integer> fields, ArrayList<String> search_values) {
		this.username = username;
		this.password = password;
		this.fields = fields;
		this.search_values = search_values;
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

	/**
	 * @return the fields
	 */
	public ArrayList<Integer> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<Integer> fields) {
		this.fields = fields;
	}

	/**
	 * @return the search_values
	 */
	public ArrayList<String> getSearch_values() {
		return search_values;
	}

	/**
	 * @param search_values the search_values to set
	 */
	public void setSearch_values(ArrayList<String> search_values) {
		this.search_values = search_values;
	}
	
	

	

}
