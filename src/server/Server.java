/**
 * 
 */
package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.rmi.ServerException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import server.database.Database;
import server.webapi.ValidateUserHandler;

import client.communicator.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Kevin
 * 
 */
public class Server {
	private int SERVER_PORT = 8080;
	private static final int MAX_WAITING_CONNECTIONS = 10;

	private static Logger logger;

	private XStream xmlstream;
	private HttpServer server;

	private static void initLog() throws IOException {

		Level logLevel = Level.FINE;

		logger = Logger.getLogger("contactmanager");
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);

		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}

	/**
	 * 
	 */
	public Server() {
		xmlstream = new XStream(new DomDriver());
	}

	public void run() {
		
		logger.info("Initializing Database");
		
		try {
			Database.initialize();		
		}
		catch (ServerException e) {
			
			//System.out.println("Could not initialize database: " + e.getMessage());
			//e.printStackTrace();

			logger.log(Level.SEVERE, e.getMessage(), e);
			
			return;
		}
		
		logger.info("Initializing HTTP Server");
		//Start the database.
		Database.initialize();
		
		//Start the HTTP server..
		server = HttpServer.create(new InetSocketAddress(SERVER_PORT), MAX_WAITING_CONNECTIONS);
	
		server.setExecutor(null);
		
		HttpHandler validateUserHandler = new HttpHandler();
		
		
		
		server.createContext("/ValidateUser", validateUserHandler);
		server.createContext("/GetProjects", getProjectsHandler);
		server.createContext("/GetSampleImage", getSampleImageHandler);
		server.createContext("/GetSampleProject", getSampleProjectHandler);
		server.createContext("/DownloadBatch", submitBatchHandler);
		server.createContext("/GetFields", getFieldHandler);
		server.createContext("/Search", searchhandler);
		server.createContext("/download", downloadFileHandler);

		logger.info("Starting HTTP Server.");
		server.start();
	}
	
	private HttpHandler validateUserHandler = new HttpHandler() {
		
		public void handle(HttpExchange exchange) throws IOException {
			//proccess the validate user request.
			ValidateUser_param param = (ValidateUser_param) xmlstream.fromXML(exchange.getRequestBody());
			ValidateUserHandler validateuserhandler;
			
			validateuserhandler = new ValidateUserHandler(param);
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			ValidateUser_result vuresult = ValidateUserHandler.validateuser();
		
			Database database = new Database();
			database.startTransaction();
			
			database.endTransaction(true);
		}
	};
	
	private HttpHandler getProjectsHandler = new HttpHandler() {
		
		public void handle(HttpExchange exchange) throws IOException {
			//proccess the validate user request.
			ValidateUser_param param = (ValidateUser_param) xmlstream.fromXML(exchange.getRequestBody());
			ValidateUserHandler validateuserhandler;
			
			validateuserhandler = new ValidateUserHandler(param);
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			ValidateUser_result vuresult = ValidateUserHandler.getproject();
		
			Database database = new Database();
			database.startTransaction();
			
			database.endTransaction(true);
		}
	};
	
	private HttpHandler getSampleImageHandler = new HttpHandler() {
		
		public void handle(HttpExchange exchange) throws IOException {
			//proccess the validate user request.
			ValidateUser_param param = (ValidateUser_param) xmlstream.fromXML(exchange.getRequestBody());
			ValidateUserHandler validateuserhandler;
			
			validateuserhandler = new ValidateUserHandler(param);
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			ValidateUser_result vuresult = ValidateUserHandler.getsampleimage();
		
			Database database = new Database();
			database.startTransaction();
			
			database.endTransaction(true);
		}
	};
	
	private HttpHandler getSampleProjectHandler = new HttpHandler() {
		
		public void handle(HttpExchange exchange) throws IOException {
			//proccess the validate user request.
			ValidateUser_param param = (ValidateUser_param) xmlstream.fromXML(exchange.getRequestBody());
			ValidateUserHandler validateuserhandler;
			
			validateuserhandler = new ValidateUserHandler(param);
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			ValidateUser_result vuresult = ValidateUserHandler.getsampleproject();
		
			Database database = new Database();
			database.startTransaction();
			
			database.endTransaction(true);
		}
	};
	
	private HttpHandler submitBatchHandler = new HttpHandler() {
		
		public void handle(HttpExchange exchange) throws IOException {
			//proccess the validate user request.
			ValidateUser_param param = (ValidateUser_param) xmlstream.fromXML(exchange.getRequestBody());
			ValidateUserHandler validateuserhandler;
			
			validateuserhandler = new ValidateUserHandler(param);
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			ValidateUser_result vuresult = ValidateUserHandler.submitbatch();
		
			Database database = new Database();
			database.startTransaction();
			
			database.endTransaction(true);
		}
	};
	
	private HttpHandler getFieldHandler = new HttpHandler() {
		
		public void handle(HttpExchange exchange) throws IOException {
			//proccess the validate user request.
			ValidateUser_param param = (ValidateUser_param) xmlstream.fromXML(exchange.getRequestBody());
			ValidateUserHandler validateuserhandler;
			
			validateuserhandler = new ValidateUserHandler(param);
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			ValidateUser_result vuresult = ValidateUserHandler.getfield();
		
			Database database = new Database();
			database.startTransaction();
			
			database.endTransaction(true);
		}
	};
	
	private HttpHandler searchhandler = new HttpHandler() {
		
		public void handle(HttpExchange exchange) throws IOException {
			//proccess the validate user request.
			ValidateUser_param param = (ValidateUser_param) xmlstream.fromXML(exchange.getRequestBody());
			ValidateUserHandler validateuserhandler;
			
			validateuserhandler = new ValidateUserHandler(param);
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			ValidateUser_result vuresult = ValidateUserHandler.search();
		
			Database database = new Database();
			database.startTransaction();
			
			database.endTransaction(true);
		}
	};
	
	private HttpHandler downloadFileHandler = new HttpHandler() {
		
		public void handle(HttpExchange exchange) throws IOException {
			//proccess the validate user request.
			DownloadBatch_param param = (DownloadBatch_param) xmlstream.fromXML(exchange.getRequestBody());
			ValidateUserHandler validateuserhandler;
			
			validateuserhandler = new DownloadFileHandler(param);
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			DownloadFile_result dfresult = DownloadFileHandler.downloadfile();
		
			Database database = new Database();
			database.startTransaction();
			
			database.endTransaction(true);
		}
	};
	
}
