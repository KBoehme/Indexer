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

	private int id;
	private int rownumber;
	private int imageID;
	private ArrayList<Value> values;
	private boolean hasvalues;

	/**
	 * @param rownumber
	 * @param projectID
	 * @param imageID
	 * @param hasvalues
	 */
	public Record() {
		this.id = -1;
		this.rownumber = -1;
		this.imageID = -1;
		values = null;
		this.hasvalues = false;
	}

	public Record(int id, int rownumber, int imageID, boolean hasvalues) {
		this.id = id;
		this.rownumber = rownumber;
		this.imageID = imageID;
		this.hasvalues = hasvalues;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the rownumber
	 */
	public int getRownumber() {
		return rownumber;
	}

	/**
	 * @param rownumber
	 *            the rownumber to set
	 */
	public void setRownumber(int rownumber) {
		this.rownumber = rownumber;
	}

	/**
	 * @return the imageID
	 */
	public int getImageID() {
		return imageID;
	}

	/**
	 * @param imageID
	 *            the imageID to set
	 */
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	/**
	 * @return the values
	 */
	public ArrayList<Value> getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(ArrayList<Value> values) {
		this.values = values;
	}

	/**
	 * @return the hasvalues
	 */
	public boolean isHasvalues() {
		return hasvalues;
	}

	/**
	 * @param hasvalues
	 *            the hasvalues to set
	 */
	public void setHasvalues(boolean hasvalues) {
		this.hasvalues = hasvalues;
	}

}
