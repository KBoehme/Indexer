/**
 * 
 */
package server.handlers;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import server.database.Database;
import shared.model.Project;
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
public class GetProjectsHandler implements
		HttpHandler {

	Database database;

	/**
	 * 
	 */
	public GetProjectsHandler() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange
	 * )
	 */
	@Override
	public void handle(HttpExchange exchange) {

		try {
			
			GetProjects_result gp_result = new GetProjects_result();
			ArrayList<Project> allprojects = null;
			database = new Database();
			database.startTransaction();
			XStream xmlstream = new XStream(new DomDriver());

			GetProjects_param param = (GetProjects_param) xmlstream
					.fromXML(exchange.getRequestBody());

			
			// Check to see if we are given valid user information befor continuing.
			boolean valid_user = database.getValidateuserdao().validateUser(
					param.getUsername(), param.getPassword(), database);

			if (valid_user) {
				//ok lets get the projects now
				ArrayList<Project> projects = database.getProjectdao().getAll(database);
				gp_result.setProjects(projects);
				gp_result.setSuccess(1);
				
				try {
					exchange.getRequestBody().close();
					exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
					xmlstream.toXML(gp_result, exchange.getResponseBody());
					exchange.getResponseBody().close();
					
				} catch (Exception e) {
					gp_result.setSuccess(2);
					xmlstream.toXML(gp_result, exchange.getResponseBody());
					e.printStackTrace();
				}
				database.endTransaction(false);
			} 
			
			else { //We have a non-valid user.
				gp_result.setSuccess(2);
				xmlstream.toXML(gp_result, exchange.getResponseBody());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
