/**
 * 
 */
package shared.communication;

/**
 * @author Kevin
 *
 */
public class GetSampleImage_param {

	private String username;
	private String password;
	private int projectid;
	private String urlprefix;
	/**
	 * 
	 */
	public GetSampleImage_param() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param username
	 * @param password
	 * @param projectid
	 */
	public GetSampleImage_param(String username, String password, int projectid) {
		this.username = username;
		this.password = password;
		this.projectid = projectid;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the projectid
	 */
	public int getProjectid() {
		return projectid;
	}
	/**
	 * @param projectid the projectid to set
	 */
	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}
	/**
	 * @return the urlprefix
	 */
	public String getUrlprefix() {
		return urlprefix;
	}
	/**
	 * @param urlprefix the urlprefix to set
	 */
	public void setUrlprefix(String urlprefix) {
		this.urlprefix = urlprefix;
	}
}
