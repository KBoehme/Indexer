/**
 * 
 */
package shared.model;

/**
 * @author Kevin
 * 
 */
public class Value {

	private int ID;
	private String value;
	private int recordID;

	/**
	 * 
	 */
	public Value() {
		// TODO Auto-generated constructor stub
		setID(-1);
		setValue(null);
		setRecordID(-1);
	}

	/**
	 * @param iD
	 * @param value
	 * @param recordID
	 */
	public Value(int iD, String value, int recordID) {
		ID = iD;
		this.value = value;
		this.recordID = recordID;
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the recordID
	 */
	public int getRecordID() {
		return recordID;
	}

	/**
	 * @param recordID
	 *            the recordID to set
	 */
	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}
}