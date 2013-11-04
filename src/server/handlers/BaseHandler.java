/**
 * 
 */
package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;

import server.database.Database;
import shared.communication.Base_result;

import com.sun.net.httpserver.HttpExchange;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author kboehme1
 * 
 */
public class BaseHandler {

	protected Database database;
	protected XStream xmlstream;

	/**
	 * Default constructor
	 */
	public BaseHandler() {
	}
	
	public void startHandler() {
		xmlstream = new XStream(new DomDriver());
		database = new Database();
		database.startTransaction();
	}
	
	public void sendOK(Base_result result, HttpExchange exchange) throws IOException {
		System.out.println("Sending OK");
		result.setSuccess(1);
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlstream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
		database.endTransaction(true);
	}

	public void sendError(Base_result result, HttpExchange exchange) throws IOException {
		result.setSuccess(2);
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlstream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
		database.endTransaction(false);
	}
	
	
	/**
	 * @return the database
	 */
	public Database getDatabase() {
		return database;
	}

	/**
	 * @param database the database to set
	 */
	public void setDatabase(Database database) {
		this.database = database;
	}

	/**
	 * @return the xmlstream
	 */
	public XStream getXmlstream() {
		return xmlstream;
	}

	/**
	 * @param xmlstream the xmlstream to set
	 */
	public void setXmlstream(XStream xmlstream) {
		this.xmlstream = xmlstream;
	}
	
}