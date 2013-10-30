/**
 * 
 */
package client.communicator;

import shared.model.User;

/**
 * @author Kevin
 * 
 * OUTPUTS If the user credentials are valid,
 * 
 * FORMAT EXAMPLE OUTPUT ::= TRUE\n <USER_FIRST_NAME>\n Sheila\n
 * <USER_LAST_NAME>\n Parker\n <NUM_RECORDS>\n 42\n
 * 
 * USER_FIRST_NAME ::= String 
 * USER_LAST_NAME ::= String
 * String NUM_RECORDS ::= Integer
 * 
 * If the user credentials are invalid,
 * 
 * FORMAT EXAMPLE OUTPUT ::= FALSE\n
 * 
 * If the operation fails for any reason (e.g., can’t connect to the server,
 * internal server error, etc.),
 * 
 * FORMAT EXAMPLE OUTPUT ::= FAILED\n
 * 
 */
public class ValidateUser_result {

	private int success;
	private User user;
	private String result_string;
	
	/**
	 * 
	 */
	public ValidateUser_result() {
		// TODO Auto-generated constructor stub
	}
	
	

	/**
	 * @param success
	 * @param firstname
	 * @param lastname
	 * @param numrecords
	 */
	public ValidateUser_result(int success, String firstname, String lastname,
			int num_indexed_records) {
		this.success = success;
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setNum_indexed_records(num_indexed_records);
	}

	/**
	 * @return the success
	 */
	public int getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(int success) {
		this.success = success;
	}
	
	public String getResultstring() {
		StringBuilder srb = new StringBuilder();
		if(this.success == 1) { //success
			srb.append("TRUE\n");
			srb.append(user.getFirstname()+"\n");
			srb.append(user.getLastname()+ "\n");
			srb.append(user.getNum_indexed_records() + "\n");
		} else if(this.success == 2) { //false
			srb.append("FALSE\n");
		} else if(this.success == 3) { //failed
			srb.append("FAILED\n");
		} else
			return null;
		
		result_string = srb.toString();
		return result_string;
	}
}
