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

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		Search_result result = new Search_result();

		//Ok we need to get a few tables to do some java logic and get what
		//we need here.
		
		try {
			super.startHandler();

			Search_param param = (Search_param) xmlstream.fromXML(exchange.getRequestBody());
			exchange.getRequestBody().close();

			boolean valid_user = database.getValidateuserdao().validateUser(param.getUsername(), param.getPassword(),
					database);
			
			if (valid_user) { //we have a valid user..
				ArrayList<Integer> fieldids = param.getFields();
				ArrayList<String> searchvalues = param.getSearch_values();

				ArrayList<Field> fields = database.getFielddao().getAll(database);
				ArrayList<Field> matched_fields = new ArrayList<Field>();
				ArrayList<Integer> projectids = new ArrayList<Integer>();
				
				HashMap<String, String> map = new HashMap<String, String>();
				//{"imageid,imagefile,rownum,fieldid" , value} map? with key-value
				
				//Match fields to IDs..
				for(Field field : fields) {
					for(int id : fieldids) {
						if(field.getID() == id) { //we have a matching ID
							matched_fields.add(field);
							projectids.add(field.getProjectID());
						}
					}
				}
				
				//ok we got all the fields.
				//lets get all the images now.
				
				ArrayList<Image> images = database.getImagedao().getAllImages(database);
				ArrayList<Image> matched_images = new ArrayList<Image>();

				ArrayList<Integer> imageids = new ArrayList<Integer>();
				
				for(int pid : projectids) {
					for(Image image: images) {
						if(image.getProjectID() == pid && image.isHasbeenindexed() == true) { //get this image.
							matched_images.add(image);
							imageids.add(image.getID());
						}
					}
				}
				
				//Ok got all the images we'll need.
				//lets get the values..
				ArrayList<Value> values = database.getValuedao().getAll(database);
				ArrayList<Value> matched_values = new ArrayList<Value>();
				
				ArrayList<String> string_values = new ArrayList<String>();

				for(int iid : imageids) {
					for(Value value : values) {
						if(value.getImageID() == iid) { //we have a value match
							matched_values.add(value);
						}
					}
				}	
				
//				//uhhh now what...
//				for(String value: string_values) {
//					for()
//				}
				
			} else {
				super.sendError(result, exchange);
			}

		} catch (Exception e) {
			super.sendError(result, exchange);
		}
	}
}
