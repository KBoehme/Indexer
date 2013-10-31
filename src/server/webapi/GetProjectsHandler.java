/**
 * 
 */
package server.webapi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import server.database.Database;
import shared.model.Project;
import client.communicator.GetProjects_result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author Kevin
 *
 */
public class GetProjectsHandler implements HttpHandler  {

	/**
	 * 
	 */
	public GetProjectsHandler() {
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		GetProjects_result gp_result = new GetProjects_result();
		ArrayList<Project> allprojects = null;
		Database database = new Database();
			
		try {
			database.startTransaction();
			allprojects = database.getProjectdao().getAll();
			database.endTransaction(true);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gp_result.setProjects(allprojects);
	}

}
