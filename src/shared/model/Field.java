/**
 * 
 */
package shared.model;

/**
 * Represents a field table as an object
 * 
 * @author Kevin
 * 
 */
public class Field {

	private int ID;
	private String title;
	private int x_cor;
	private int width;
	private int field_number;
	private String helphtml;
	private String knowndata;
	private int projectID;

	/**
	 * 
	 */
	public Field() {
		// TODO Auto-generated constructor stub
		this.ID = -1;
		this.title = null;
		this.x_cor = -1;
		this.width = -1;
		this.field_number = -1;
		this.helphtml = null;
		this.knowndata = null;
		this.projectID = -1;
	}

	/**
	 * @param iD
	 * @param title
	 * @param x_cor
	 * @param width
	 * @param field_number
	 * @param helphtml
	 * @param knowndata
	 * @param projectID
	 */
	public Field(int iD, String title, int x_cor, int width, String helphtml, String knowndata, int field_number,
			int projectID) {
		this.ID = iD;
		this.title = title;
		this.x_cor = x_cor;
		this.width = width;
		this.field_number = field_number;
		this.helphtml = helphtml;
		this.knowndata = knowndata;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the x_cor
	 */
	public int getX_cor() {
		return x_cor;
	}

	/**
	 * @param x_cor
	 *            the x_cor to set
	 */
	public void setX_cor(int x_cor) {
		this.x_cor = x_cor;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the field_number
	 */
	public int getField_number() {
		return field_number;
	}

	/**
	 * @param field_number
	 *            the field_number to set
	 */
	public void setField_number(int field_number) {
		this.field_number = field_number;
	}

	/**
	 * @return the helphtml
	 */
	public String getHelphtml() {
		return helphtml;
	}

	/**
	 * @param helphtml
	 *            the helphtml to set
	 */
	public void setHelphtml(String helphtml) {
		this.helphtml = helphtml;
	}

	/**
	 * @return the knowndata
	 */
	public String getKnowndata() {
		return knowndata;
	}

	/**
	 * @param knowndata
	 *            the knowndata to set
	 */
	public void setKnowndata(String knowndata) {
		this.knowndata = knowndata;
	}

	/**
	 * @return the projectID
	 */
	public int getProjectID() {
		return projectID;
	}

	/**
	 * @param projectID
	 *            the projectID to set
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
}
