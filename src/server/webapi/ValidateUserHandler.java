/**
 * 
 */
package server.webapi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import server.database.Database;
import client.communicator.ValidateUser_param;
import client.communicator.ValidateUser_result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;

/**
 * @author Kevin
 *
 */
public class ValidateUserHandler implements HttpHandler {

	/**
	 * Default Constructor
	 */
	public ValidateUserHandler() {
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		XStream xmlstream = new XStream();
		//proccess the validate user request.
		ValidateUser_param param = (ValidateUser_param) xmlstream.fromXML(exchange.getRequestBody());

		String username = param.getUsername();
		String password = param.getPassword();
		
		Database database = new Database();
		try {
			database.getUserdao().getUser(database);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ok lets make sure this is ok.
		
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
	
		database.startTransaction();
		
		database.endTransaction(true);
	}
}
