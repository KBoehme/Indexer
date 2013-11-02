/**
 * 
 */
package shared.communication;

import java.util.ArrayList;

import shared.model.Field;

/**
 * @author Kevin
 *
 */
public class GetFields_result extends Base_result {

	private ArrayList<Field> fields;
	private String result_string;

	/**
	 * 
	 */
	public GetFields_result() {
		super();
	}

	/**
	 * @return the fields
	 */
	public ArrayList<Field> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}


	public String getResultstring() {
		StringBuilder srb = new StringBuilder();
		if(super.getSuccess() == 1) { //success
			for(Field field : fields) {
				srb.append(field.getProjectID() + "\n");
				srb.append(field.getID() + "\n");
				srb.append(field.getTitle() + "\n");
			}
			
		} else if(super.getSuccess() == 2) { //failed
			srb.append("FAILED\n");
		} else
			return null;
		
		result_string = srb.toString();
		return result_string;
	}
}
