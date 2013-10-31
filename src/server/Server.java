/**
 * 
 */
package server;

import java.io.IOException;
import java.net.InetSocketAddress;

import server.database.Database;
import server.webapi.DownloadFileHandler;
import server.webapi.GetFieldsHandler;
import server.webapi.GetProjectsHandler;
import server.webapi.GetSampleImageHandler;
import server.webapi.GetSampleProjectsHandler;
import server.webapi.SearchHandler;
import server.webapi.SubmitBatchHandler;
import server.webapi.ValidateUserHandler;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * @author Kevin
 * 
 */
public class Server {
	private static int SERVER_PORT = 45000;
	private static final int MAX_WAITING_CONNECTIONS = 10;

	private HttpServer server;

	/**
	 * 
	 */
	public Server() {
	}

	public Server(int port) {
		System.out.println("port on server = " + port);
		SERVER_PORT = port;
	}

	public void run() throws IOException {

		// Start the database.
		Database.initialize();

		// Start the HTTP server..
		server = HttpServer.create(new InetSocketAddress(SERVER_PORT),
				MAX_WAITING_CONNECTIONS);

		server.setExecutor(null);

		HttpHandler validateUserHandler = new ValidateUserHandler();
		HttpHandler getProjectsHandler = new GetProjectsHandler();
		HttpHandler getSampleImageHandler = new GetSampleImageHandler();
		HttpHandler getSampleProjectsHandler = new GetSampleProjectsHandler();
		HttpHandler submitBatchHandler = new SubmitBatchHandler();
		HttpHandler getFieldsHandler = new GetFieldsHandler();
		HttpHandler searchhandler = new SearchHandler();
		HttpHandler downloadFileHandler = new DownloadFileHandler();

		server.createContext("/ValidateUser", validateUserHandler);
		server.createContext("/GetProjects", getProjectsHandler);
		server.createContext("/GetSampleImage", getSampleImageHandler);
		server.createContext("/GetSampleProject", getSampleProjectsHandler);
		server.createContext("/DownloadBatch", submitBatchHandler);
		server.createContext("/GetFields", getFieldsHandler);
		server.createContext("/Search", searchhandler);
		server.createContext("/download", downloadFileHandler);

		server.start();
	}
}
