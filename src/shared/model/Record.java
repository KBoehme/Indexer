/**
 * 
 */
package shared.model;

import java.util.ArrayList;

/**
 * Record represents a single record
 * 
 * @author Kevin
 * 
 */
public class Record {

	private int rownumber;
	private int projectID;
	private int imageID;
	private boolean hasvalues;
	
	
	
	/**
	 * @param rownumber
	 * @param projectID
	 * @param imageID
	 * @param hasvalues
	 */
	public Record() {
		this.rownumber = -1;
		this.projectID = -1;
		this.imageID = -1;
		this.hasvalues = false;
	}
	
	
	public Record(int rownumber, int projectID, int imageID, boolean hasvalues) {
		this.rownumber = rownumber;
		this.projectID = projectID;
		this.imageID = imageID;
		this.hasvalues = hasvalues;
	}
	/**
	 * @return the rownumber
	 */
	public int getRownumber() {
		return rownumber;
	}
	/**
	 * @param rownumber the rownumber to set
	 */
	public void setRownumber(int rownumber) {
		this.rownumber = rownumber;
	}
	/**
	 * @return the projectID
	 */
	public int getProjectID() {
		return projectID;
	}
	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	/**
	 * @return the imageID
	 */
	public int getImageID() {
		return imageID;
	}
	/**
	 * @param imageID the imageID to set
	 */
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}


	/**
	 * @return the hasvalues
	 */
	public boolean isHasvalues() {
		return hasvalues;
	}

	/**
	 * @param hasvalues the hasvalues to set
	 */
	public void setHasvalues(boolean hasvalues) {
		this.hasvalues = hasvalues;
	}
	
	
}
