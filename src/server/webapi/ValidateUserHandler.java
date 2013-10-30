/**
 * 
 */
package server.webapi;

import java.io.IOException;

import client.communicator.ValidateUser_param;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author Kevin
 *
 */
public class ValidateUserHandler implements HttpHandler {

	/**
	 * 
	 */
	public ValidateUserHandler(ValidateUser_param param) {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
		
		
	}
}
