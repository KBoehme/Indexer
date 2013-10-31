package server.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import server.databaseaccess.FieldDAO;
import server.databaseaccess.ImageDAO;
import server.databaseaccess.ProjectDAO;
import server.databaseaccess.RecordDAO;
import server.databaseaccess.UserDAO;
import server.databaseaccess.ValueDAO;

/**
 * 
 */

/**
 * <p>
 * This is the main database class. It containts the connection to the server.
 * It creates DAO's for all the tables as well (Users, Projects etc.)
 * 
 * </p>
 * 
 * @author Kevin
 * 
 */
public class Database {

	private Connection connection = null;

	private FieldDAO fielddao;
	private ImageDAO imagedao;
	private ProjectDAO projectdao;
	private RecordDAO recorddao;
	private UserDAO userdao;
	private ValueDAO valuedao;
	
	private static String dbFile = "indexer_server.sqlite";
	private static String dbName = "database" + File.separator + dbFile;
	private static String connectionURL = "jdbc:sqlite:"+dbName;
	private final static String driver = "org.sqlite.JDBC";
	
	/**
	 * Default Constructor
	 */
	public Database() {
		fielddao = new FieldDAO(this);
		imagedao = new ImageDAO(this);
		projectdao = new ProjectDAO(this);
		recorddao = new RecordDAO(this);
		userdao = new UserDAO(this);
		valuedao = new ValueDAO(this);
		System.out.println("const. database");
	}
	
	public static void initialize() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		connection = null;
		try {
			Database.initialize();
			connection = DriverManager
					.getConnection(connectionURL);
			System.out.println("Connected to the database.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public void startTransaction() {
		// logger.entering("server.database.Database", "startTransaction");
		Connection connection = this.getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void endTransaction(boolean commit) {
		try {
			if (commit == true) {
				connection.commit();
			} else {
				connection.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		connection = null;
	}

	/**
	 * @return the fielddao
	 */
	public FieldDAO getFielddao() {
		return fielddao;
	}

	/**
	 * @param fielddao
	 *            the fielddao to set
	 */
	public void setFielddao(FieldDAO fielddao) {
		this.fielddao = fielddao;
	}

	/**
	 * @return the imagedao
	 */
	public ImageDAO getImagedao() {
		return imagedao;
	}

	/**
	 * @param imagedao
	 *            the imagedao to set
	 */
	public void setImagedao(ImageDAO imagedao) {
		this.imagedao = imagedao;
	}

	/**
	 * @return the projectdao
	 */
	public ProjectDAO getProjectdao() {
		return projectdao;
	}

	/**
	 * @param projectdao
	 *            the projectdao to set
	 */
	public void setProjectdao(ProjectDAO projectdao) {
		this.projectdao = projectdao;
	}

	/**
	 * @return the recorddao
	 */
	public RecordDAO getRecorddao() {
		return recorddao;
	}

	/**
	 * @param recorddao
	 *            the recorddao to set
	 */
	public void setRecorddao(RecordDAO recorddao) {
		this.recorddao = recorddao;
	}

	/**
	 * @return the userdao
	 */
	public UserDAO getUserdao() {
		return userdao;
	}

	/**
	 * @param userdao
	 *            the userdao to set
	 */
	public void setUserdao(UserDAO userdao) {
		this.userdao = userdao;
	}

	/**
	 * @return the valuedao
	 */
	public ValueDAO getValuedao() {
		return valuedao;
	}

	/**
	 * @param valuedao
	 *            the valuedao to set
	 */
	public void setValuedao(ValueDAO valuedao) {
		this.valuedao = valuedao;
	}

}
