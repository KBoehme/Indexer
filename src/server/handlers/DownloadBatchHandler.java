/**
 * 
 */
package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import server.database.Database;
import shared.model.Field;
import shared.model.Image;
import shared.model.Project;
import shared.model.User;
import client.communicator.DownloadBatch_param;
import client.communicator.DownloadBatch_result;
import client.communicator.GetProjects_param;
import client.communicator.GetProjects_result;

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

		try {
			
			DownloadBatch_result image_result = new DownloadBatch_result();
			Image image = new Image();
			database = new Database();
			database.startTransaction();
			XStream xmlstream = new XStream(new DomDriver());

			DownloadBatch_param param = (DownloadBatch_param) xmlstream
					.fromXML(exchange.getRequestBody());

			
			// Check to see if we are given valid user information befor continuing.
			boolean valid_user = database.getValidateuserdao().validateUser(
					param.getUsername(), param.getPassword(), database);

			if (valid_user) {
				//ok lets get the projects now
				
				Project project = database.getProjectdao().getProject(param.getProjectid(), database);
				image = database.getImagedao().getImage(param.getProjectid(), database);
				ArrayList<Field> fields = database.getFielddao().getFields(param.getProjectid(), database);
				
				image_result.setFields(fields);
				image_result.setImage(image);
				image_result.setProject(project);
				
				image_result.setSuccess(1);
				User user = new User();
				user.setID(database.getUserdao().getUser(param.getUsername(), param.getPassword(), database).getID());
				user.setCurr_batch_id(image.getID());
				database.getUserdao().update(user, database);
				
				try {
					exchange.getRequestBody().close();
					exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
					xmlstream.toXML(image_result, exchange.getResponseBody());
					exchange.getResponseBody().close();
					
				} catch (Exception e) {
					image_result.setSuccess(2);
					xmlstream.toXML(image_result, exchange.getResponseBody());
					e.printStackTrace();
				}
				database.endTransaction(true);
			} 
			
			else { //We have a non-valid user.
				image_result.setSuccess(2);
				xmlstream.toXML(image_result, exchange.getResponseBody());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
