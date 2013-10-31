/**
 * 
 */
package client.communicator;

import java.net.URL;

import shared.model.Image;

/**
 * GET SAMPLE IMAGE
 * 
 * Returns a sample image for the specified project
 * 
 * INPUTS USER ::= String User�s name 
 * PASSWORD ::= String User�s password
 * PROJECT ::= Integer Project ID
 * 
 * OUTPUTS If the operation succeeds,
 * 
 * FORMAT EXAMPLE OUTPUT ::= <IMAGE_URL>\n
 * 
 * IMAGE_URL ::= URL
 * 
 * If the operation fails for any reason (e.g., invalid project ID, invalid
 * user name or password, can�t connect to the server, internal server
 * error, etc.),
 * 
 * FORMAT EXAMPLE OUTPUT ::= FAILED\n FAILED\n
 * 
 * @author Kevin
 *
 */
public class GetSampleImage_result {
	
	private int success;
	private Image image;
	private String result_string;

	/**
	 * 
	 */
	public GetSampleImage_result() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param success
	 * @param url
	 * @param result_string
	 */
	public GetSampleImage_result(int success, String url, String result_string) {
		this.success = success;
		image.setFileurl(url);
		this.result_string = result_string;
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
			srb.append(image.getFileurl() + "\n");
		} else if(this.success == 2) { //false
			srb.append("FAILED\n");
		} else
			return null;
		
		result_string = srb.toString();
		return result_string;
	}
	
}
