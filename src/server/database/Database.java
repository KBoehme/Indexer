package server.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import server.databaseaccess.*;

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

	private Connection connection;

	private FieldDAO fielddao;
	private ImageDAO imagedao;
	private ProjectDAO projectdao;
	private RecordDAO recorddao;
	private UserDAO userdao;
	private ValueDAO valuedao;
	private ValidateUserDAO validateuserdao;

	private static String dbFile = "indexer_server.sqlite";
	private static String dbName = "database" + File.separator + dbFile;
	private static String connectionURL = "jdbc:sqlite:" + dbName;
	private final static String driver = "org.sqlite.JDBC";

	/**
	 * Default Constructor
	 */
	public Database() {
		connection = null;
		fielddao = new FieldDAO();
		imagedao = new ImageDAO();
		projectdao = new ProjectDAO();
		recorddao = new RecordDAO();
		userdao = new UserDAO();
		valuedao = new ValueDAO();
		validateuserdao = new ValidateUserDAO();
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
		return connection;
	}

	public void startTransaction() {
		// logger.entering("server.database.Database", "startTransaction");
		connection = this.getConnection();
		try {
			connection = DriverManager.getConnection(connectionURL);
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

	/**
	 * @return the validateuserdao
	 */
	public ValidateUserDAO getValidateuserdao() {
		return validateuserdao;
	}

	/**
	 * @param validateuserdao
	 *            the validateuserdao to set
	 */
	public void setValidateuserdao(ValidateUserDAO validateuserdao) {
		this.validateuserdao = validateuserdao;
	}

}
