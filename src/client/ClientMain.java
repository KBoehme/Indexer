/**
 * 
 */
package client;


import server.Server;

/**  Runs the client
 * @author Kevin
 *
 */
public class ClientMain {

	static String host = "localhost";
	static int port = 39640;
	/**
	 * 
	 */
	public ClientMain() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			host = args[0];
			port = Integer.parseInt(args[1]);
			ClientCommunicator cc = new ClientCommunicator(host, port);
			LoginForm loginform = new LoginForm(cc);
			loginform.setVisible(true);
		} catch(Exception e) {
			//bad input args
			
		}

	}

}
