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

	private int ID;
	private ArrayList<String> values;

	/**
	 * Default constructor
	 */
	public Record() {
		// TODO Auto-generated constructor stub
		setID(-1);
		setValues(null);
	}

	/**
	 * @param iD
	 * @param values
	 */
	public Record(int iD, ArrayList<String> values) {
		ID = iD;
		this.values = values;
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
	 * @return the values
	 */
	public ArrayList<String> getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(ArrayList<String> values) {
		for (String s : values) {
			values.add(s);
		}
	}

}
