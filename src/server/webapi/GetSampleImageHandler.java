/**
 * 
 */
package server.webapi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.ArrayList;

import server.database.Database;
import shared.model.Image;
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
	public void handle(HttpExchange exchange) throws IOException {

		super.handle(exchange); //I think this runs validate user... maybe.... i hope...
		
		XStream xmlstream = new XStream(new DomDriver());
		GetSampleImage_param param = (GetSampleImage_param) xmlstream.fromXML(exchange.getRequestBody());
		
		GetSampleImage_result gsi_result = new GetSampleImage_result();
		ArrayList<Image> images = null;
		URL imageUrl = null;
		database = new Database();
		
		try {
			database.startTransaction();
			images = database.getImagedao().getAllImages();
			database.endTransaction(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed: Could handle get samle image.");
		}
		if (isvaliduser) {
			for(Image image : images) {
				if (image.getProjectID() == param.getProjectid()) {
					//imageUrl = new URL(param.getUrlprefix() + image.getFileurl());
					gsi_result.setImage(image);
					break;
				}
			}
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlstream.toXML(gsi_result, exchange.getResponseBody());
		exchange.close();
	}
}
