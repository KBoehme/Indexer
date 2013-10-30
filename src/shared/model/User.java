/**
 * 
 */
package shared.model;

/**
 * Represents a User table as an object
 * 
 * @author Kevin
 * 
 */
public class User {

	private int ID;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private int num_indexed_records;
	private int curr_batch_id;

	/**
	 * 
	 */
	public User() {
		// TODO Auto-generated constructor stub
		username = null;
		password = null;
		firstname = null;
		lastname = null;
		email = null;
		num_indexed_records = 0;
		curr_batch_id = -1;
	}

	/**
	 * @param username
	 * @param password
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param num_indexed_records
	 * @param curr_batch_id
	 */
	public User(String username, String password, String firstname,
			String lastname, String email, int num_indexed_records,
			int curr_batch_id) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.num_indexed_records = num_indexed_records;
		this.curr_batch_id = curr_batch_id;
	}

	/**
	 * @return
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
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
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the num_indexed_records
	 */
	public int getNum_indexed_records() {
		return num_indexed_records;
	}

	/**
	 * @param num_indexed_records
	 *            the num_indexed_records to set
	 */
	public void setNum_indexed_records(int num_indexed_records) {
		this.num_indexed_records = num_indexed_records;
	}

	/**
	 * @return the curr_batch_id
	 */
	public int getCurr_batch_id() {
		return curr_batch_id;
	}

	/**
	 * @param curr_batch_id
	 *            the curr_batch_id to set
	 */
	public void setCurr_batch_id(int curr_batch_id) {
		this.curr_batch_id = curr_batch_id;
	}
}
