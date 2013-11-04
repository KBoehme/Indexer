/**
 * 
 */
package server.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import shared.communication.Search_param;
import shared.communication.Search_result;
import shared.communication.SubmitBatch_param;
import shared.model.Field;
import shared.model.Image;
import shared.model.Project;
import shared.model.Record;
import shared.model.User;
import shared.model.Value;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author Kevin
 * 
 */
public class SearchHandler extends BaseHandler implements HttpHandler {

	/**
	 * 
	 */
	public SearchHandler() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		Search_result result = new Search_result();

		// Ok we need to get a few tables to do some java logic and get what
		// we need here.

		try {
			super.startHandler();

			Search_param param = (Search_param) xmlstream.fromXML(exchange.getRequestBody());
			exchange.getRequestBody().close();

			boolean valid_user = database.getValidateuserdao().validateUser(param.getUsername(), param.getPassword(),
					database);

			if (valid_user) { // we have a valid user..

				// new plan loop through all of the field ids first.

				ArrayList<Integer> fieldids = param.getFields();

				for (int fieldid : fieldids) {
					// ok for each field id we now need to get the fields that have those ids.
					Field field = database.getFielddao().getField(fieldid, database);
					int fieldnum = field.getField_number();
					// ok we have the field and can get the projectid...
					int projectid = field.getProjectID();
					ArrayList<Image> images = database.getImagedao().getSearchImages(projectid, database);
					// ok got the images that we need to check the values on..

					for (Image image : images) { // loop through the images..
						ArrayList<Value> values = database.getValuedao().getValues(image.getID(), field.getField_number(), database);
						// ok we now have all the values that we need to search through..
						for (String svalue : param.getSearch_values()) {
							for (Value value : values) {
								//System.out.println("search value: " + svalue + "  vs  " + value.getValue());
								if (value.getValue().toLowerCase().equals(svalue.toLowerCase())) { // we have a matched search
																		// value..
									// {"imageid,imagefile,rownum,fieldid" , value} map? with
									// key-value
								//	System.out.println("BINGOOOOOOOOOOoo");
									int row_number = database.getRecorddao().getRecordRowNumber(value.getRecordID(),
											database);
									result.addSearchTuple(image.getID(), image.getFileurl(), row_number, fieldid);

								}

							}
						}

					}

				}

				//ok we seem to have gotten all the search fields found...
				super.sendOK(result, exchange);
			} else {
				super.sendError(result, exchange);
			}

		} catch (Exception e) {
			super.sendError(result, exchange);
		}
	}
}
