package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import server.Server;
import shared.communication.DownloadBatch_param;
import shared.communication.DownloadBatch_result;
import shared.communication.GetFields_param;
import shared.communication.GetFields_result;
import shared.communication.GetProjects_param;
import shared.communication.GetProjects_result;
import shared.communication.GetSampleImage_param;
import shared.communication.GetSampleImage_result;
import shared.communication.Search_param;
import shared.communication.Search_result;
import shared.communication.SubmitBatch_param;
import shared.communication.SubmitBatch_result;
import shared.communication.ValidateUser_param;
import shared.communication.ValidateUser_result;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Kevin Boehme (kboehme1)
 * 
 */
public class ClientCommunicator {
	private String SERVER_HOST;
	private int SERVER_PORT;
	private String URL_PREFIX;

	/**
	 * This is a constuctor with default port and localhost.
	 */
	public ClientCommunicator() {
		// TODO Auto-generated constructor stub
		this.SERVER_HOST = "localhost";
		this.SERVER_PORT = 8080;
		this.URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;

		Server server = new Server(SERVER_PORT);
		try {
			server.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Constructor that takes host and port.
	 * 
	 * @param host
	 * @param port
	 */
	public ClientCommunicator(String host, int port) {
		SERVER_HOST = host;
		SERVER_PORT = port;
		this.URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;

		Server server = new Server(SERVER_PORT);
		try {
			server.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * VALIDATE USER Validates user credentials
	 * 
	 * @param params
	 * @return
	 */
	public ValidateUser_result validateUser(ValidateUser_param params) {
		ValidateUser_result res = (ValidateUser_result) doPost("/ValidateUser", params);
		return res;
	}

	/**GET PROJECTS Returns information about all of the available projects
	 * 
	 * @param params
	 * @return
	 */
	public GetProjects_result getProjects(GetProjects_param params) {
		return (GetProjects_result) doPost("/GetProjects", params);
	}

	/**
	 * GET SAMPLE IMAGE Returns a sample image for the specified project
	 * 
	 * @param params
	 * @return
	 */
	public GetSampleImage_result getSampleImage(GetSampleImage_param params) {
		GetSampleImage_result results = (GetSampleImage_result) doPost("/GetSampleImage", params);
		results.getImage().setFileurl(URL_PREFIX + "/" + "Download/" + results.getImage().getFileurl());
		downloadFile(results.getImage().getFileurl());
		return results;
	}

	/**
	 * DOWNLOAD BATCH Downloads a batch for the user to index
	 * 
	 * @param params
	 * @return
	 */
	public DownloadBatch_result downloadBatch(DownloadBatch_param params) {
		DownloadBatch_result results = (DownloadBatch_result) doPost("/DownloadBatch", params);
		// downloadFiles(results.toString());
		return results;
	}

	/**
	 * SUBMIT BATCH Submits the indexed record field values for a batch to the Server
	 * 
	 * @param params
	 * @return
	 */
	public SubmitBatch_result submitBatch(SubmitBatch_param params) {
		return (SubmitBatch_result) doPost("/SubmitBatch", params);
	}

	/**
	 * GET FIELDS Returns information about all of the fields for the specified project If no project is specified, returns
	 * information about all of the fields for all projects in the system
	 * 
	 * OUTPUT ::= FAILED\n FAILED\n
	 * 
	 * @param params
	 * @return
	 */
	public GetFields_result getFields(GetFields_param params) {
		return (GetFields_result) doPost("/GetFields", params);
	}

	/**
	 * SEARCH Searches the indexed records for the specified string.
	 * 
	 * @param params
	 * @return Search_result
	 */
	public Search_result search(Search_param params) {
		Search_result results = (Search_result) doPost("/Search", params);
		//downloadFile(results.toString());
		return results;
	}

	/**
	 * DOWNLOAD FILE
	 * 
	 * @param linktofile
	 * @return
	 */
	public void downloadFile(String linktofile) {
		// "http://localhost:39640/Download/users/guest/k/kboehme1/git/Indexer/Recordsimages/1890_image0.png"
		
		doGet(linktofile);
	}

	/**
	 * doGet performs a HTTP get request.
	 * 
	 * @param urlpath
	 */
	private void doGet(String urlpath) {
		HttpURLConnection connection = null;

		try {
			URL url = new URL(urlpath);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "test/html");
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				byte[] bytes = IOUtils.toByteArray(connection.getInputStream());
				writeFile(bytes);
			} else {
				System.out.println("Unable to write file..");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.getInputStream().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection.disconnect();

		}

	}

	/**
	 * doPost performs a post HTTP post request.
	 * 
	 * @param urlPath
	 * @param postdata
	 * @return
	 */
	private Object doPost(String urlPath, Object postdata) {
		HttpURLConnection connection = null;
		Object response = null;
		XStream xmlstream = new XStream(new DomDriver());

		try {
			URL url = new URL(URL_PREFIX + urlPath);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.connect();

			xmlstream.toXML(postdata, connection.getOutputStream());
			connection.getOutputStream().close();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// It worked.
				response = xmlstream.fromXML(connection.getInputStream());
			} else {

			}
			connection.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("we have io error");
			e.printStackTrace();
		} finally {
			try {
				connection.getInputStream().close();
				connection.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return response;
	}

	/**
	 * This function takes in file lists and downloads each one
	 * 
	 * @param filelist
	 */
	private void writeFile(byte[] bytes) {
		File file = new File("./image.png");
		try {
			FileOutputStream fout = new FileOutputStream(file);
			IOUtils.write(bytes, fout);
			for (Byte bite : bytes) {
				fout.write(bite);
			}
			fout.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
