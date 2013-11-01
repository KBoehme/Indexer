/**
 * 
 */
package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.ArrayList;

import server.database.Database;
import shared.model.Image;
import shared.model.Project;
import shared.model.User;
import client.communicator.GetProjects_result;
import client.communicator.GetSampleImage_param;
import client.communicator.GetSampleImage_result;
import client.communicator.ValidateUser_param;
import client.communicator.ValidateUser_result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Kevin
 * 
 */
public class GetSampleImageHandler extends ValidateUserHandler implements HttpHandler {

	/**
	 * 
	 */
	public GetSampleImageHandler() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) {
		
		try {
			
			GetSampleImage_result image_result = new GetSampleImage_result();
			database = new Database();
			database.startTransaction();
			XStream xmlstream = new XStream(new DomDriver());
			
			GetSampleImage_param param = (GetSampleImage_param) xmlstream.fromXML(exchange.getRequestBody());
			
			// Check to see if we are given valid user information befor continuing.
			boolean valid_user = database.getValidateuserdao().validateUser(
					param.getUsername(), param.getPassword(), database);
			
			if (valid_user) {
				//ok lets get the projects now
				Image image = database.getImagedao().getImage(param.getProjectid(), database);
				image_result.setImage(image);
				if(image != null)
					image_result.setSuccess(1);
				else
					image_result.setSuccess(2);
				
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
				database.endTransaction(false);
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
