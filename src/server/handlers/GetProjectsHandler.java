/**
 * 
 */
package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.GetProjects_param;
import shared.communication.GetProjects_result;
import shared.model.Project;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Kevin
 * 
 */
public class GetProjectsHandler extends BaseHandler implements HttpHandler {

	/**
	 * 
	 */
	public GetProjectsHandler() {

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange
	 * )
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		GetProjects_result result = new GetProjects_result();

		try {

			super.startHandler();

			GetProjects_param param = (GetProjects_param) xmlstream
					.fromXML(exchange.getRequestBody());
			exchange.getRequestBody().close();

			boolean valid_user = database.getValidateuserdao().validateUser(
					param.getUsername(), param.getPassword(), database);

			if (valid_user) {
				ArrayList<Project> projects = database.getProjectdao().getAll(
						database);
				result.setProjects(projects);
				super.sendOK(result, exchange);
			}

			else { // We have a non-valid user.
				super.sendError(result, exchange);
			}
		} catch (Exception e) {

			super.sendError(result, exchange);
		}
	}
}
