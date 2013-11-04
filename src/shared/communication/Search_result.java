/**
 * 
 */
package shared.communication;

import java.net.URL;
import java.util.ArrayList;

/**
 * This class is the result of a field search with a given string.
 * 
 * @author Kevin
 * 
 */
public class Search_result extends Base_result {

	/*
	 * We need to keep a bunch of tuples of the form (Batch ID, Image URL, Record Number, Field ID)
	 */

	private ArrayList<SearchTuple> searchtuples;
	private String result_string;

	/**
	 * 
	 */
	public Search_result() {
		// TODO Auto-generated constructor stub
		searchtuples = new ArrayList<SearchTuple>();
	}

	public String getResultstring() {
		StringBuilder srb = new StringBuilder();
		System.out.println("num: " + searchtuples.size());
		if (super.getSuccess() == 1) { // success
			for (SearchTuple st : searchtuples) {
				srb.append(st.getImageID() + "\n");
				srb.append(st.getImageurl() + "\n");
				srb.append(st.getRecord_number() + "\n");
				srb.append(st.getFieldID() + "\n");
			}
		} else if (super.getSuccess() == 2) { // failed
			srb.append("FAILED\n");
		} else
			return null;

		result_string = srb.toString();
		return result_string;
	}

	/**
	 * @return the searchtuples
	 */
	public ArrayList<SearchTuple> getSearchtuples() {
		return searchtuples;
	}

	/**
	 * @param searchtuples
	 *            the searchtuples to set
	 */
	public void setSearchtuples(ArrayList<SearchTuple> searchtuples) {
		this.searchtuples = searchtuples;
	}
	
	public void addSearchTuple(int imageID, String imageurl, int record_number, int fieldID) {
		SearchTuple st = new SearchTuple(imageID, imageurl, record_number, fieldID);
		searchtuples.add(st);
	}

	private class SearchTuple {
		private int imageID;
		private String imageurl;
		private int record_number;
		private int fieldID;

		/**
		 * @param iD
		 * @param imageurl
		 * @param record_number
		 * @param fieldID
		 */
		private SearchTuple(int imageID, String imageurl, int record_number, int fieldID) {
			this.imageID = imageID;
			this.imageurl = imageurl;
			this.record_number = record_number;
			this.fieldID = fieldID;
		}

		/**
		 * @return the iD
		 */
		private int getImageID() {
			return imageID;
		}

		/**
		 * @param iD
		 *            the iD to set
		 */
		private void setImageID(int imageID) {
			imageID = imageID;
		}

		/**
		 * @return the imageurl
		 */
		private String getImageurl() {
			return imageurl;
		}

		/**
		 * @param imageurl
		 *            the imageurl to set
		 */
		private void setImageurl(String imageurl) {
			this.imageurl = imageurl;
		}

		/**
		 * @return the record_number
		 */
		private int getRecord_number() {
			return record_number;
		}

		/**
		 * @param record_number
		 *            the record_number to set
		 */
		private void setRecord_number(int record_number) {
			this.record_number = record_number;
		}

		/**
		 * @return the fieldID
		 */
		private int getFieldID() {
			return fieldID;
		}

		/**
		 * @param fieldID
		 *            the fieldID to set
		 */
		private void setFieldID(int fieldID) {
			this.fieldID = fieldID;
		}

	}

}
