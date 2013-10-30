/**
 * 
 */
package client.communicator;

import java.util.ArrayList;

import shared.model.Project;

/**
 * GET PROJECTS
 * 
 * Returns information about all of the available projects
 * 
 * INPUTS 
 * USER ::= String User’s name 
 * PASSWORD ::= String User’s password
 * 
 * OUTPUTS If the operation succeeds,
 * 
 * FORMAT EXAMPLE OUTPUT ::= <PROJECT_INFO>\n
 * PROJECT_INFO ::= <PROJECT_ID>\n  <PROJECT_TITLE>\n
 * PROJECT_ID ::= Integer\n 
 * PROJECT_TITLE ::= String
 * 
 * 
 * If the operation fails for any reason (e.g., invalid user name or
 * password, can’t connect to the server, internal server error, etc.),
 * 
 * FORMAT EXAMPLE OUTPUT ::= FAILED\n
 * 
 * @author Kevin
 *
 */
public class GetProjects_result {

	private int success; // 1 = success, 2= failed
	private ArrayList<Project> projects;
	
	private String result_string;


	/**
	 * 
	 */
	public GetProjects_result() {
		// TODO Auto-generated constructor stub
	}
	
	public String getResultstring() {
		StringBuilder srb = new StringBuilder();

		if(this.success == 1) {
			for(Project project : projects) {
				srb.append(project.getID()+"\n");
				srb.append(project.getTitle()+ "\n");
			}
		} else if (this.success == 2) { //false
			srb.append("FAILED\n");
		} else
			return null;
			
		result_string = srb.toString();
		return result_string;
	}
	
}
