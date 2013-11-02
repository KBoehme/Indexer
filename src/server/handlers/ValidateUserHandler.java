/**
 * 
 */
package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import server.database.Database;
import shared.communication.SubmitBatch_param;
import shared.communication.ValidateUser_param;
import shared.communication.ValidateUser_result;
import shared.model.User;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Kevin
 * 
 */
public class ValidateUserHandler extends BaseHandler implements HttpHandler {

	/**
	 * Default Constructor
	 */
	public ValidateUserHandler() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		ValidateUser_result result = new ValidateUser_result();
		User user = null;
		try {
			super.startHandler();
			
			ValidateUser_param param = (ValidateUser_param) xmlstream.fromXML(exchange.getRequestBody());
			exchange.getRequestBody().close();
			
			user = database.getUserdao().getUser(param.getUsername(), param.getPassword(), database);
			
			boolean valid_user = super.database.getValidateuserdao().validateUser(
					param.getUsername(), param.getPassword(), super.database);

			if (valid_user) {
				System.out.println("here");
				result.setUser(user);
				
				super.sendOK(result, exchange);
				
			} else {
				super.sendError(result, exchange);
			}

		} catch (Exception e) {
			super.sendError(result, exchange);
		}
	}
}
