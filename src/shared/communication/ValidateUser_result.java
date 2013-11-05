/**
 * 
 */
package shared.communication;

import shared.model.User;

/**
 * @author Kevin
 */
public class ValidateUser_result extends Base_result {

	private User user = new User();

	/**
	 * 
	 */
	public ValidateUser_result() {
		super();
		user = null;
	}
	
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}


	/**
	 * @param success
	 * @param firstname
	 * @param lastname
	 * @param numrecords
	 */
	public ValidateUser_result(String firstname, String lastname, int num_indexed_records) {
		user = new User();
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setNum_indexed_records(num_indexed_records);
	}
	
	public String getResultstring() {
		StringBuilder srb = new StringBuilder();
		String string = "";
		if(super.getSuccess() == 1) {
			srb.append("TRUE\n");
			srb.append(user.getFirstname() + "\n");
			srb.append(user.getLastname() + "\n");
			srb.append(user.getNum_indexed_records() + "\n");
		} else if(super.getSuccess() == 2){
			srb.append("FALSE\n");
		} else {
			srb.append("FAILED\n");
		}

		string = srb.toString();
		return string;
	}
}
