/**
 * 
 */
package shared.communication;

import java.util.ArrayList;

import shared.model.Record;

/**
 * @author Kevin
 *
 */
public class SubmitBatch_param {

	private String username;
	private String password;
	private int imageid;
	private ArrayList<Record> record_values;
	
	/**
	 * 
	 */
	public SubmitBatch_param() {
		
	}
	
	/**
	 * @param username
	 * @param password
	 * @param imageid
	 * @param record_values
	 */
	public SubmitBatch_param(String username, String password, int imageid, ArrayList<Record> record_values) {
		// TODO Auto-generated constructor stub
		this.username = username;
		this.password = password;
		this.imageid = imageid;
		this.record_values = record_values;
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
	 * @return the imageid
	 */
	public int getImageid() {
		return imageid;
	}

	/**
	 * @param imageid the imageid to set
	 */
	public void setImageid(int imageid) {
		this.imageid = imageid;
	}

	/**
	 * @return the record_values
	 */
	public ArrayList<Record> getRecord_values() {
		return record_values;
	}

	/**
	 * @param record_values the record_values to set
	 */
	public void setRecord_values(ArrayList<Record> record_values) {
		this.record_values = record_values;
	}
}
