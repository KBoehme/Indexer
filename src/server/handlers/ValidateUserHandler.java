/**
 * 
 */
package server.handlers;

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
	public void handle(HttpExchange exchange){
		try {
			database = new Database();
			database.startTransaction();
			XStream xmlstream = new XStream(new DomDriver());
			
			ValidateUser_param param = (ValidateUser_param) xmlstream.fromXML(exchange.getRequestBody());
			
			try {
				exchange.getRequestBody().close();
			} catch (IOException e) {
				System.out.println("IO exception in the server.");
			}

			ValidateUser_result user_result = null;
			User user = null;

			try {
				user = new User();
				Database.initialize();
				database.startTransaction();
				user = database.getUserdao().getUser(param.getUsername(), param.getPassword(), database);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(user != null) {
				isvaliduser = true;
				user_result = new ValidateUser_result(user.getFirstname(), user.getLastname(), user.getNum_indexed_records());
			} else {
				user_result = new ValidateUser_result();
			}
			
			try {
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			xmlstream.toXML(user_result, exchange.getResponseBody());
			
			try {
				exchange.getResponseBody().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			database.endTransaction(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
