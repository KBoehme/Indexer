/**
 * 
 */
package client.communicator;

import shared.model.Image;

/**
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

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
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
