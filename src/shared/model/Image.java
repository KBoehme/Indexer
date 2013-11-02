/**
 * 
 */
package shared.model;

import java.net.URL;

/** Represents an image table as an object
 * 
 * @author Kevin
 *
 */
/**
 * @author Kevin
 * 
 */
public class Image {

	private int ID;
	private String fileurl;
	private int projectID;
	private boolean hasbeenindexed;

	/**
	 * Default constructor
	 */
	public Image() {
		// TODO Auto-generated constructor stub
		setID(-1);
		setFileurl(null);
		setProjectID(-1);
		hasbeenindexed = false;
	}

	/**
	 * @param iD
	 * @param file
	 * @param projectID
	 */
	public Image(int iD, String fileurl, int projectID, boolean hasbeenindexed) {
		this.ID = iD;
		this.fileurl = fileurl;
		this.projectID = projectID;
		this.hasbeenindexed = hasbeenindexed;
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the file
	 */
	public String getFileurl() {
		return fileurl;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	/**
	 * @return the recordsgroupID
	 */
	public int getProjectID() {
		return projectID;
	}

	/**
	 * @param recordsgroupID
	 *            the recordsgroupID to set
	 */
	public void setProjectID(int recordsgroupID) {
		this.projectID = recordsgroupID;
	}

	/**
	 * @return the hasbeenindexed
	 */
	public boolean isHasbeenindexed() {
		return hasbeenindexed;
	}

	/**
	 * @param hasbeenindexed the hasbeenindexed to set
	 */
	public void setHasbeenindexed(boolean hasbeenindexed) {
		this.hasbeenindexed = hasbeenindexed;
	}
	
	

}
