/**
 * 
 */
package shared.model;

/** This represents a Project table as an object
 * @author Kevin
 *
 */
public class Project {
	
	private int ID;
	private String title;
	private int recordsperimage;
	private int numfields;
	private int firstycoord;
	private int recordheight;

	/**Default Constructor
	 * 
	 */
	public Project() {
		// TODO Auto-generated constructor stub
		setID(-1);
		setTitle(null);
		setRecordsperimage(-1);
		setNumfields(-1);
		setFirstycoord(-1);
		setRecordheight(-1);
	}
	
	
	/**
	 * @param iD
	 * @param title
	 * @param recordsperimage
	 * @param firstycoord
	 * @param recordheight
	 * @param fieldgroupID
	 * @param imagegroupdID
	 */
	public Project(int iD, String title, int recordsperimage, int numfields, int firstycoord,
			int recordheight) {
		ID = iD;
		this.title = title;
		this.recordsperimage = recordsperimage;
		setNumfields(numfields);
		this.firstycoord = firstycoord;
		this.recordheight = recordheight;
	}


	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}


	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}


	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @return the recordsperimage
	 */
	public int getRecordsperimage() {
		return recordsperimage;
	}


	/**
	 * @param recordsperimage the recordsperimage to set
	 */
	public void setRecordsperimage(int recordsperimage) {
		this.recordsperimage = recordsperimage;
	}


	/**
	 * @return the numfields
	 */
	public int getNumfields() {
		return numfields;
	}


	/**
	 * @param numfields the numfields to set
	 */
	public void setNumfields(int numfields) {
		this.numfields = numfields;
	}


	/**
	 * @return the firstycoord
	 */
	public int getFirstycoord() {
		return firstycoord;
	}


	/**
	 * @param firstycoord the firstycoord to set
	 */
	public void setFirstycoord(int firstycoord) {
		this.firstycoord = firstycoord;
	}


	/**
	 * @return the recordheight
	 */
	public int getRecordheight() {
		return recordheight;
	}


	/**
	 * @param recordheight the recordheight to set
	 */
	public void setRecordheight(int recordheight) {
		this.recordheight = recordheight;
	}
	
}
