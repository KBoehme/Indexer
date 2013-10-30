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
	private URL fileurl;
	private int projectID;

	/**
	 * Default constructor
	 */
	public Image() {
		// TODO Auto-generated constructor stub
		setID(-1);
		setFileurl(null);
		setProjectID(-1);
	}

	/**
	 * @param iD
	 * @param file
	 * @param projectID
	 */
	public Image(int iD, URL fileurl, int projectID) {
		this.ID = iD;
		this.fileurl = fileurl;
		this.projectID = projectID;
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
	public URL getFileurl() {
		return fileurl;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFileurl(URL fileurl) {
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

}
