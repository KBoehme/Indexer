/**
 * 
 */
package client.communicator;

import java.util.ArrayList;

import shared.model.Field;

/**
 * @author Kevin
 *
 */
public class GetFields_result {

	private int success;
	private ArrayList<Field> fields;
	
	private String result_string;

	/**
	 * 
	 */
	public GetFields_result() {
		// TODO Auto-generated constructor stub
	}
	
	public String getResultstring() {
		StringBuilder srb = new StringBuilder();
		if(this.success == 1) { //success
			for(Field field : fields) {
				srb.append(field.getProjectID() + "\n");
				srb.append(field.getID() + "\n");
				srb.append(field.getTitle() + "\n");
			}
			
		} else if(this.success == 2) { //failed
			srb.append("FAILED\n");
		} else
			return null;
		
		result_string = srb.toString();
		return result_string;
	}
}
