/**
 * 
 */
package shared.communication;

import java.util.ArrayList;

import shared.model.Project;

/**
 * GET PROJECTS
 * 
 * @author Kevin
 *
 */
public class GetProjects_result extends Base_result {

	private ArrayList<Project> projects;
	private String result_string;


	/**
	 * 
	 */
	public GetProjects_result() {
	}
	
	public String getResultstring() {
		StringBuilder srb = new StringBuilder();

		if(super.getSuccess() == 1) {
			for(Project project : projects) {
				srb.append(project.getID()+"\n");
				srb.append(project.getTitle()+ "\n");
			}
		} else if (super.getSuccess() == 2) { //false
			srb.append("FAILED\n");
		} else
			return null;
			
		result_string = srb.toString();
		return result_string;
	}
	
	/**
	 * @return the projects
	 */
	public ArrayList<Project> getProjects() {
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}

	/**
	 * @return the result_string
	 */
	public String getResult_string() {
		return result_string;
	}

	/**
	 * @param result_string the result_string to set
	 */
	public void setResult_string(String result_string) {
		this.result_string = result_string;
	}

}
