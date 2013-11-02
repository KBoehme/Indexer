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
import shared.communication.GetProjects_result;
import shared.communication.GetSampleImage_param;
import shared.communication.GetSampleImage_result;
import shared.communication.ValidateUser_param;
import shared.communication.ValidateUser_result;
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
public class GetSampleImageHandler extends BaseHandler implements HttpHandler {

	/**
	 * 
	 */
	public GetSampleImageHandler() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		GetSampleImage_result result = new GetSampleImage_result();

		try {
			super.startHandler();

			GetSampleImage_param param = (GetSampleImage_param) xmlstream.fromXML(exchange.getRequestBody());
			exchange.getRequestBody().close();

			boolean valid_user = database.getValidateuserdao().validateUser(param.getUsername(), param.getPassword(),
					database);

			if (valid_user) {
				Image image = database.getImagedao().getImage(param.getProjectid(), database, false);

				if (image != null) {
					result.setImage(image);
					super.sendOK(result, exchange);
				} else {
					super.sendError(result, exchange);
				}

			} else { // We have a non-valid user.
				super.sendError(result, exchange);
			}
		} catch (Exception e) {
			super.sendError(result, exchange);
		}
	}
}
