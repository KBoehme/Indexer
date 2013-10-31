/**
 * 
 */
package server.webapi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import server.database.Database;
import shared.model.User;
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
public class ValidateUserHandler implements HttpHandler {

	boolean isvaliduser = false;
	Database database;

	/**
	 * Default Constructor
	 */
	public ValidateUserHandler() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		XStream xmlstream = new XStream(new DomDriver());
		// proccess the validate user request.
		ValidateUser_param param = (ValidateUser_param) xmlstream.fromXML(exchange.getRequestBody());
		database = new Database();
		String username = param.getUsername();
		String password = param.getPassword();


		User user = null;
		try {
			Database.initialize();
			database.startTransaction();
			user = database.getUserdao().getUser(username, password);
			database.endTransaction(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//lets build the validateuser result.
		ValidateUser_result user_result = null;
		if(user != null) {
			user_result = new ValidateUser_result(user.getFirstname(), user.getLastname(), user.getNum_indexed_records());
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlstream.toXML(user_result, exchange.getResponseBody());
		exchange.close();
	}
}
