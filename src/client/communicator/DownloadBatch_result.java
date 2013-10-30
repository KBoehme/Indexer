/**
 * 
 */
package client.communicator;

import java.util.ArrayList;

import shared.model.Field;
import shared.model.Image;
import shared.model.Project;

/**
 * DOWNLOAD BATCH
 * 
 * Downloads a batch for the user to index
 * 
 * The Server should assign the user a batch from the requested project. The
 * Server should not return batches that are already assigned to another user.
 * If the user already has a batch assigned to them, this operation should fail
 * (i.e., a user is not allowed to have multiple batches assigned to them at the
 * same time).
 * 
 * Note that the known values URL may not be present for some fields.
 * 
 * INPUTS USER ::= String User’s name PASSWORD ::= String User’s password
 * PROJECT ::= Integer Project ID
 * 
 * OUTPUTS If the operation succeeds,
 * 
 * FORMAT EXAMPLE OUTPUT ::= <BATCH_ID>\n <PROJECT_ID>\n <IMAGE_URL>\n
 * <FIRST_Y_COORD>\n <RECORD_HEIGHT>\n <NUM_RECORDS>\n <NUM_FIELDS>\n
 * 
 * <FIELD>+ 23\n FIELD ::= <FIELD_ID>\n Last Name\n <FIELD_NUM>\n
 * http://localhost:1234/help/last.html\n <FIELD_TITLE>\n 125\n <HELP_URL>\n
 * 250\n <X_COORD>\n http://localhost:1234/known/last.txt\n <PIXEL_WIDTH>\n 16\n
 * (<KNOWN_VALUES_URL>\n)? 2\n First Name\n
 * 
 * BATCH_ID ::= Integer PROJECT_ID ::= Integer 375\n FIELD_ID ::= Integer
 * IMAGE_URL ::= URL FIRST_Y_COORD ::= Integer RECORD_HEIGHT ::= Integer
 * NUM_RECORDS ::= Integer NUM_FIELDS ::= Integer FIELD_NUM ::= Integer
 * FIELD_TITLE ::= String HELP_URL ::= URL X_COORD ::= Integer PIXEL_WIDTH ::=
 * Integer KNOWN_VALUES_URL ::= URL
 * 
 * If the operation fails for any reason (e.g., invalid project ID, invalid user
 * name or password, the user already has a batch assigned to them, can’t
 * connect to the server, internal server error, etc.),
 * 
 * FORMAT EXAMPLE OUTPUT ::= FAILED\n FAILED\n
 * 
 * @author Kevin
 * 
 */
public class DownloadBatch_result {

	//do we need these?
	private int num_records;
	private int num_fields;
//-----------------------------
	
	
	private int success;
	private Project project;
	private Image image;
	private ArrayList<Field> fields;

	private String result_string;

	/**
	 * 
	 */
	public DownloadBatch_result() {
		// TODO Auto-generated constructor stub
	}

	public String getResultstring() {
		StringBuilder srb = new StringBuilder();
		if(this.success == 1) { //success
			srb.append(image.getID() + "\n");
			srb.append(project.getID() + "\n");
			srb.append(image.getFileurl() + "\n");
			srb.append(project.getFirstycoord() + "\n");
			srb.append(project.getRecordheight() + "\n");
			srb.append(this.num_records + "\n");
			srb.append(this.num_fields + "\n");
			for(Field field : fields) {
				srb.append(field.getID() + "\n");
				srb.append(field.getField_number() + "\n");
				srb.append(field.getTitle() + "\n");
				srb.append(field.getHelphtml() + "\n");
				srb.append(field.getX_cor() + "\n");
				srb.append(field.getWidth() + "\n");
				if(field.getKnowndata() != null) {
					srb.append(field.getKnowndata() + "\n");
				}
			}

		} else if(this.success == 2) { //failed
			srb.append("FAILED\n");
		} else
			return null;
		
		result_string = srb.toString();
		return result_string;
	}

}
