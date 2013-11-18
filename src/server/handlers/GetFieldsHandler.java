/**
 * 
 */
package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.GetFields_param;
import shared.communication.GetFields_result;
import shared.communication.GetProjects_param;
import shared.communication.GetProjects_result;
import shared.model.Field;
import shared.model.Project;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Kevin
 * 
 */
public class GetFieldsHandler extends BaseHandler implements HttpHandler {

	/**
	 * 
	 */
	public GetFieldsHandler() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange
	 * )
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		GetFields_result result = new GetFields_result();

		try {
			super.startHandler();

			GetFields_param param = (GetFields_param) xmlstream
					.fromXML(exchange.getRequestBody());
			exchange.getRequestBody().close();

			boolean valid_user = database.getValidateuserdao().validateUser(
					param.getUsername(), param.getPassword(), database);
			if (valid_user || param.getUsername().equals("ADMIN")) {
				if (param.getProjectid() == 0) { // we need to get all the projects..
					ArrayList<Field> fields = database.getFielddao().getAll(database);
					result.setFields(fields);
					super.sendOK(result, exchange);

				} else { // just try to get the one they asked for.
					Project project = database.getProjectdao().getProject(
							param.getProjectid(), database);
					boolean projectexists = true;
					if (project == null)
						super.sendError(result, exchange);
					else {
						ArrayList<Field> fields = database.getFielddao().getFields(param.getProjectid(), database);
						result.setFields(fields);
						super.sendOK(result, exchange);

					}
				}
			} else { // We have a non-valid user.
				super.sendError(result, exchange);
			}

		} catch (Exception e) {
			e.printStackTrace();
			super.sendError(result, exchange);
		}
	}

}
