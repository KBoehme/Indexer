/**
 * 
 */
package shared.communication;

import shared.model.Image;

/**
 * @author Kevin
 *
 */
public class GetSampleImage_result extends Base_result {
	
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
	public GetSampleImage_result(String url) {
		image.setFileurl(url);
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

	/**
	 * @param result_string the result_string to set
	 */
	public void setResult_string(String result_string) {
		this.result_string = result_string;
	}

	public String getResultstring() {
		StringBuilder srb = new StringBuilder();
		if(super.getSuccess() == 1) { //success
			srb.append(image.getFileurl() + "\n");
		} else if(super.getSuccess() == 2) { //false
			srb.append("FAILED\n");
		} else
			return null;
		
		result_string = srb.toString();
		return result_string;
	}

}
