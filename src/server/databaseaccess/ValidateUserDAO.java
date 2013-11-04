/**
 * 
 */
package server.databaseaccess;

import java.sql.SQLException;

import server.database.Database;
import shared.model.User;



/**
 * @author kboehme1
 *
 */

public class ValidateUserDAO {
	
	
	
	/**
	 * 
	 */
	public ValidateUserDAO() {
		
	}

	public boolean validateUser(String username, String password, Database database) {
		
		User user = null;
		try {
			user = database.getUserdao().getUser(username, password, database);
			if(user == null)
				return false;
			else
				return true;	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}