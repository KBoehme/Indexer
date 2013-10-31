/**
 * 
 */
import server.database.Database;

/**
 * @author Kevin
 *
 */
public class Main {

	/**
	 * 
	 */
	public Main() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("=============================================");
		System.out.println("||   This will be my testing main method   ||");
		System.out.println("=============================================");
		
		//XMLParser xm = new XMLParser();
		System.out.println("Lets test the Database a little...");
		Database dbtester = new Database();
		dbtester.getConnection();
	}
}
