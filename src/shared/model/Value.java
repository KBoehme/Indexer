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
	private int fieldnum;
	private int rownum;
	private int imageID;
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
	 * @param fieldnum
	 * @param rownum
	 * @param imageID
	 * @param recordID
	 */
	public Value(int iD, String value, int fieldnum, int rownum, int imageID,
			int recordID) {
		super();
		ID = iD;
		this.value = value;
		this.fieldnum = fieldnum;
		this.rownum = rownum;
		this.imageID = imageID;
		this.recordID = recordID;
	}

	/**
	 * @return the fieldnum
	 */
	public int getFieldnum() {
		return fieldnum;
	}

	/**
	 * @param fieldnum
	 *            the fieldnum to set
	 */
	public void setFieldnum(int fieldnum) {
		this.fieldnum = fieldnum;
	}

	/**
	 * @return the rownum
	 */
	public int getRownum() {
		return rownum;
	}

	/**
	 * @param rownum
	 *            the rownum to set
	 */
	public void setRownum(int rownum) {
		this.rownum = rownum;
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