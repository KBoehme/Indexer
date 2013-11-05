/**
 * 
 */
package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.DownloadBatch_param;
import shared.communication.DownloadBatch_result;
import shared.communication.GetProjects_param;
import shared.communication.GetProjects_result;
import shared.model.Field;
import shared.model.Image;
import shared.model.Project;
import shared.model.User;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Kevin
 *
 */
public class DownloadBatchHandler implements HttpHandler  {

	Database database;

	
	/**
	 * 
	 */
	public DownloadBatchHandler() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		DownloadBatch_result image_result = new DownloadBatch_result();
		XStream xmlstream = new XStream(new DomDriver());

		try {
			Image image = new Image();
			database = new Database();
			database.startTransaction();

			DownloadBatch_param param = (DownloadBatch_param) xmlstream
					.fromXML(exchange.getRequestBody());
			exchange.getRequestBody().close();

			
			// Check to see if we are given valid user information before continuing.
			boolean valid_user = database.getValidateuserdao().validateUser(
					param.getUsername(), param.getPassword(), database);
			
			if (valid_user) {
				User user = new User();
				user = database.getUserdao().getUser(param.getUsername(), param.getPassword(), database);
				//lets check if they have a batch or not already.
				if(user.getCurr_batch_id() == -1) { // we can continue...

					//ok lets get the projects now
					Project project = database.getProjectdao().getProject(param.getProjectid(), database);
					System.out.println(project.getID());
					image = database.getImagedao().getImage(param.getProjectid(), database, true);
					ArrayList<Field> fields = database.getFielddao().getFields(param.getProjectid(), database);
					
					image_result.setFields(fields);
					image_result.setImage(image);
					image_result.setProject(project);
					
					image_result.setSuccess(1);				
					user.setCurr_batch_id(image.getID());
					database.getUserdao().update(user, database);
					
					try {
						exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
						xmlstream.toXML(image_result, exchange.getResponseBody());
						exchange.getResponseBody().close();
						
					} catch (Exception e) {
						image_result.setSuccess(2);
						xmlstream.toXML(image_result, exchange.getResponseBody());
						e.printStackTrace();
					}
					
				} else { //hes already got a batch.
					image_result.setSuccess(2);
					exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
					xmlstream.toXML(image_result, exchange.getResponseBody());
					exchange.getResponseBody().close();
				}
				
			} 
			
			else { //We have a non-valid user.
				image_result.setSuccess(2);
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				xmlstream.toXML(image_result, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
		} catch (Exception e) {
			image_result.setSuccess(2);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlstream.toXML(image_result, exchange.getResponseBody());
			exchange.getResponseBody().close();
			e.printStackTrace();
		}
		database.endTransaction(true);
	}

}
