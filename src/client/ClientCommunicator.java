package client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import server.Server;
import client.communicator.DownloadBatch_param;
import client.communicator.DownloadBatch_result;
import client.communicator.GetFields_param;
import client.communicator.GetFields_result;
import client.communicator.GetProjects_param;
import client.communicator.GetProjects_result;
import client.communicator.GetSampleImage_param;
import client.communicator.GetSampleImage_result;
import client.communicator.Search_param;
import client.communicator.Search_result;
import client.communicator.SubmitBatch_param;
import client.communicator.SubmitBatch_result;
import client.communicator.ValidateUser_param;
import client.communicator.ValidateUser_result;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Kevin Boehme (kboehme1)
 * 
 */
public class ClientCommunicator {
	private XStream xmlstream;
	private String SERVER_HOST;
	private int SERVER_PORT;
	private String URL_PREFIX;
	private static String HTTP_POST = "POST";
	private static String HTTP_GET = "GET";

	/**
	 * This is a constuctor
	 */
	public ClientCommunicator() {
		// TODO Auto-generated constructor stub
		this.SERVER_HOST = "localhost";
		this.SERVER_PORT = 8080;
		this.URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
		xmlstream = new XStream(new DomDriver());	
		
		Server server = new Server(SERVER_PORT);
		try {
			server.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("server failed to run");

		}
	}
	
	public ClientCommunicator(String host, int port) {
		SERVER_HOST = host;
		SERVER_PORT = port;
		this.URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
		xmlstream = new XStream(new DomDriver());
		
		Server server = new Server(SERVER_PORT);
		try {
			server.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("server failed to run");
		}
	}
	
	//Make a constructor that takes in custom server information.
	

	/**
	 * VALIDATE USER Validates user credentials
	 * <p>
	 * INPUTS
	 * 
	 * USER ::= String User�s name PASSWORD ::= String User�s password
	 * 
	 * OUTPUTS If the user credentials are valid,
	 * 
	 * FORMAT EXAMPLE OUTPUT ::= TRUE\n <USER_FIRST_NAME>\n <USER_LAST_NAME>\n <NUM_RECORDS>\n
	 * 
	 * USER_FIRST_NAME ::= String USER_LAST_NAME ::= String String NUM_RECORDS ::= Integer
	 * 
	 * If the user credentials are invalid,
	 * 
	 * FORMAT EXAMPLE OUTPUT ::= FALSE\n
	 * 
	 * If the operation fails for any reason (e.g., can�t connect to the server, internal server
	 * error, etc.),
	 * 
	 * FORMAT EXAMPLE OUTPUT ::= FAILED\n
	 * </p>
	 * 
	 * @param params
	 * @return
	 */
	public ValidateUser_result validateUser(ValidateUser_param params) {
		return (ValidateUser_result) doPost("/ValidateUser", params);
	}

	/*
	 * GET PROJECTS
	 * 
	 * Returns information about all of the available projects
	 * 
	 * INPUTS USER ::= String User�s name PASSWORD ::= String User�s password
	 * 
	 * OUTPUTS If the operation succeeds,
	 * 
	 * FORMAT EXAMPLE OUTPUT ::= <PROJECT_INFO>\n
	 * 
	 * PROJECT_INFO ::= <PROJECT_ID>\n<PROJECT_TITLE>\n PROJECT_ID ::= Integer\n PROJECT_TITLE ::=
	 * String
	 * 
	 * 
	 * If the operation fails for any reason (e.g., invalid user name or password, can�t connect to
	 * the server, internal server error, etc.),
	 * 
	 * FORMAT EXAMPLE OUTPUT ::= FAILED\n FAILED\n
	 * 
	 * 
	 * @param params
	 * 
	 * @return
	 */
	public GetProjects_result getProjects(GetProjects_param params) {
		return (GetProjects_result) doPost("/getProjects", params);
	}

	/**
	 * GET SAMPLE IMAGE
	 * 
	 * Returns a sample image for the specified project
	 * 
	 * INPUTS USER ::= String User�s name PASSWORD ::= String User�s password PROJECT ::= Integer
	 * Project ID
	 * 
	 * OUTPUTS If the operation succeeds,
	 * 
	 * FORMAT EXAMPLE OUTPUT ::= <IMAGE_URL>\n http://localhost:1234/images/batch-1.png\n IMAGE_URL
	 * ::= URL
	 * 
	 * If the operation fails for any reason (e.g., invalid project ID, invalid user name or
	 * password, can�t connect to the server, internal server error, etc.),
	 * 
	 * FORMAT EXAMPLE OUTPUT ::= FAILED\n FAILED\n
	 * 
	 * @param params
	 * @return
	 */
	public GetSampleImage_result getSampleImage(GetSampleImage_param params) {
		GetSampleImage_result results = (GetSampleImage_result) doPost("/getSampleImage", params);
		downloadFiles(results.toString());
		return results;
	}

	/**
	 * DOWNLOAD BATCH
	 * 
	 * Downloads a batch for the user to index
	 * 
	 * The Server should assign the user a batch from the requested project. The Server should not
	 * return batches that are already assigned to another user. If the user already has a batch
	 * assigned to them, this operation should fail (i.e., a user is not allowed to have multiple
	 * batches assigned to them at the same time).
	 * 
	 * Note that the known values URL may not be present for some fields.
	 * 
	 * INPUTS USER ::= String User�s name PASSWORD ::= String User�s password PROJECT ::= Integer
	 * Project ID
	 * 
	 * OUTPUTS If the operation succeeds,
	 * 
	 * FORMAT EXAMPLE OUTPUT ::= <BATCH_ID>\n 4\n <PROJECT_ID>\n 1\n <IMAGE_URL>\n
	 * http://localhost:1234/images/img-4.png\n <FIRST_Y_COORD>\n 100\n <RECORD_HEIGHT>\n 50\n
	 * <NUM_RECORDS>\n 7\n <NUM_FIELDS>\n 2\n <FIELD>+ 23\n 1\n FIELD ::= <FIELD_ID>\n Last Name\n
	 * <FIELD_NUM>\n http://localhost:1234/help/last.html\n <FIELD_TITLE>\n 125\n <HELP_URL>\n 250\n
	 * <X_COORD>\n http://localhost:1234/known/last.txt\n <PIXEL_WIDTH>\n 16\n
	 * (<KNOWN_VALUES_URL>\n)? 2\n First Name\n BATCH_ID ::= Integer
	 * http://localhost:1234/help/first.html\n PROJECT_ID ::= Integer 375\n FIELD_ID ::= Integer
	 * 250\n IMAGE_URL ::= URL http://localhost:1234/known/first.txt\n FIRST_Y_COORD ::= Integer
	 * 20\n RECORD_HEIGHT ::= Integer 3\n NUM_RECORDS ::= Integer Age\n NUM_FIELDS ::= Integer
	 * http://localhost:1234/help/age.html\n FIELD_NUM ::= Integer (>= 1) 625\n FIELD_TITLE ::=
	 * String 100\n HELP_URL ::= URL X_COORD ::= Integer PIXEL_WIDTH ::= Integer KNOWN_VALUES_URL
	 * ::= URL
	 * 
	 * If the operation fails for any reason (e.g., invalid project ID, invalid user name or
	 * password, the user already has a batch assigned to them, can�t connect to the server,
	 * internal server error, etc.),
	 * 
	 * FORMAT EXAMPLE OUTPUT ::= FAILED\n FAILED\n
	 * 
	 * @param params
	 * @return
	 */
	public DownloadBatch_result downloadBatch(DownloadBatch_param params) {
		DownloadBatch_result results = (DownloadBatch_result) doPost("/downloadBatch", params);
		downloadFiles(results.toString());
		return results;
	}

	/**
	 * SUBMIT BATCH
	 * 
	 * Submits the indexed record field values for a batch to the Server
	 * 
	 * The Server should un-assign the user from the submitted batch. The Server should increment
	 * the total number of records indexed by the user so that the system can tell the user how many
	 * records they have indexed each time they log in. (NOTE: This is the number of individual
	 * names the user has indexed, not the number of batches. To simplify this calculation, when a
	 * batch is submitted, give the user credit for indexing all records on the batch, even if they
	 * didn�t do them all.)
	 * 
	 * After a batch has been submitted, the Server should allow the batch to be searched by key
	 * word.
	 * 
	 * INPUTS
	 * 
	 * USER ::= String User�s name PASSWORD ::= String User�s password BATCH ::= Integer Batch ID
	 * RECORD_VALUES ::= Comma-separated list of record values ordered String(,String)* by a
	 * left-to-right, top-to-bottom traversal of the image
	 * 
	 * OUTPUTS If the operation succeeds,
	 * 
	 * FORMAT EXAMPLE OUTPUT ::= TRUE\n
	 * 
	 * If the operation fails for any reason (e.g., invalid batch ID, invalid user name or password,
	 * user doesn�t own the submitted batch, wrong number of values, can�t connect to the server,
	 * internal server error, etc.),
	 * 
	 * FORMAT EXAMPLE OUTPUT ::= FAILED\n
	 * 
	 * @param params
	 * @return
	 */
	public SubmitBatch_result submitBatch(SubmitBatch_param params) {
		return (SubmitBatch_result) doPost("/submitBatch", params);
	}

	/**
	 * GET FIELDS
	 * 
	 * Returns information about all of the fields for the specified project If no project is
	 * specified, returns information about all of the fields for all projects in the system
	 * 
	 * INPUTS USER ::= String User�s name PASSWORD ::= String User�s password PROJECT ::= Integer |
	 * Empty
	 * 
	 * OUTPUTS If the operation succeeds,
	 * 
	 * FORMAT EXAMPLE OUTPUT ::= <FIELD_INFO>+ 2\n 1\n FIELD_INFO ::= <PROJECT_ID>\n Last Name\n
	 * <FIELD_ID>\n 2\n <FIELD_TITLE>\n PROJECT_ID ::= Integer 2\n FIELD_ID ::= Integer 3\n
	 * FIELD_TITLE ::= String Gender\n 2\n 4\n Age\n
	 * 
	 * If the operation fails for any reason (e.g., invalid project ID, invalid user name or
	 * password, can�t connect to the server, internal server error, etc.),
	 * 
	 * FORMAT EXAMPLE
	 * 
	 * OUTPUT ::= FAILED\n FAILED\n
	 */

	/**
	 * @param params
	 * @return
	 */
	public GetFields_result getFields(GetFields_param params) {
		return (GetFields_result) doPost("/getFields", params);
	}

	/**
	 * SEARCH
	 * 
	 * Searches the indexed records for the specified strings
	 * 
	 * The user specifies one or more fields to be searched, and one or more strings to search for.
	 * The fields to be searched are specified by �field ID�. (Note, field IDs are unique across all
	 * fields in the system.)
	 * 
	 * The Server searches all indexed records containing the specified fields for the specified
	 * strings, and returns a list of all matches. In order to constitute a match, a value must
	 * appear in one of the search fields, and be exactly equal (ignoring case) to one of the search
	 * strings.
	 * 
	 * For each match found, the Server returns a tuple of the following form:
	 * 
	 * (Batch ID, Image URL, Record Number, Field ID)
	 * 
	 * Batch ID is the ID of the batch containing the match. Image URL is the URL of the batch�s
	 * image file on the Server. Record Number is the number of the record (or row) on the batch
	 * that contains the match (top-most record is number one, the one below it is number two,
	 * etc.). Field ID is the ID of the field in the record that contains the match (this is the
	 * field�s �ID�, not its �number�).
	 * 
	 * Intuitively, Search works by OR-ing the requirements together. For example, if the user
	 * searches fields 1, 7 for values �a�, �b�, �c�, the result contains all matches for which the
	 * field is 1 OR 7 and the value is �a� OR �b� OR �c�.
	 * 
	 * Alternatively, we could say that the result is equivalent to the union of the following
	 * searches: Field 1 for �a� Field 1 for �b� Field 1 for �c� Field 7 for �a� Field 7 for �b�
	 * Field 7 for �c�
	 * 
	 * INPUTS USER ::= String User�s name PASSWORD ::= String User�s password FIELDS ::=
	 * Comma-separated list of fields to be searched <FIELD_ID>(,<FIELD_ID>)* FIELD_ID ::= Integer
	 * SEARCH_VALUES ::= Comma-separated list of strings to search for String(,String)*
	 * 
	 * OUTPUTS If the operation succeeds,
	 * 
	 * FORMAT EXAMPLE OUTPUT ::= <SEARCH_RESULT>* 4\n http://localhost:1234/images/img-4.png\n
	 * SEARCH_RESULT ::= 3\n <BATCH_ID>\n 14\n <IMAGE_URL>\n 7\n <RECORD_NUM>\n
	 * http://localhost:1234/images/img-7.png\n <FIELD_ID>\n 5\n 9\n BATCH_ID ::= Integer IMAGE_URL
	 * ::= URL RECORD_NUM ::= Integer (>= 1) FIELD_ID ::= Integer
	 * 
	 * If the operation fails for any reason (e.g., invalid user name or password, invalid field ID,
	 * no search values, can�t connect to the server, internal server error, etc.),
	 * 
	 * FORMAT EXAMPLE OUTPUT ::= FAILED\n FAILED\n
	 * 
	 * 
	 * @param params
	 * @return Search_result
	 */
	/**
	 * @param params
	 * @return
	 */
	public Search_result search(Search_param params) {
		Search_result results = (Search_result) doPost("/search", params);
		downloadFiles(results.toString());
		return results;
	}

	/**
	 * File Downloads The previous section defines the web service operations that your Server (and
	 * Client Communicator) must support. Some of these operations return URLs that reference image,
	 * known data, or field help files that reside in the Server�s file system. When a Client
	 * receives such a URL, it must download the contents (i.e., bytes) of the file referenced by
	 * the URL. For example, suppose the Client receives the following image URL from the Server:
	 * 
	 * http://localhost:1234/images/batch-1.png
	 * 
	 * Before the Client can display the image to the user, it must first retrieve (i.e., download)
	 * the bytes of the image file from the Server. Similarly, if the Client receives the URL of a
	 * known data file or a field help file from the Server, it must download the content of those
	 * files before it can use them.
	 * 
	 * Clients will use HTTP GET requests to download files from your Server. To make this work,
	 * your Server should implement an HTTP Handler that does the following: 1) Extracts the
	 * requested URL from the HTTP GET request (i.e., from the HttpExchange) 2) Maps the Path
	 * portion of the requested URL to the path of the requested file in the Server�s file system.
	 * (For example, if the requested URL is http://localhost:1234/images/batch-1.png, and your
	 * Server stores image files in a directory named /users/fred/cs240/record-indexer/images, the
	 * Path from the requested URL (/images/batch-1.png) would map to the file path
	 * /users/fred/cs240/record-indexer/images/batch-1.png) 3) Opens the requested file, and passes
	 * back the contents (i.e., bytes) of the file back to the client through the HTTP response body
	 * (i.e., through the HttpExchange)
	 * 
	 * NOTE: The file download HTTP Handler is different from the other HTTP Handlers in your
	 * Server. The other HTTP Handlers process web service requests from the Client, and use object
	 * serialization to receive inputs from and pass outputs to the Client. However, the file
	 * download HTTP Handler does not use object serialization at all. It simply returns the bytes
	 * of the requested file back to the client through the HttpExchange.
	 * 
	 * To ensure that your file download HTTP Handler is working properly, you can test it with a
	 * regular web browser. Just type the URL of the file you want to download into your web
	 * browser�s URL field, and enter return. The browser will use an HTTP GET request to download
	 * the file from your Server, and display the contents of the file in your browser. If this
	 * doesn�t work, something is wrong with your file download.
	 * 
	 * On the Client side, you should add an operation to your Client Communicator class that will
	 * download a file from your Server. It should take a URL as input, and pass back the contents
	 * (i.e., bytes) of the requested file as output. Internally, this operation should use an HTTP
	 * GET request to download the contents of the file from your Server.
	 * 
	 * 
	 * @param linktofile
	 * @return
	 */
	public void downloadFile(String linktofile) {
		String[] string_split = linktofile.split("\\n");
		for (String string : string_split) {
			try {
				URL url = new URL(string);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void doGet(URL url) {
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(HTTP_GET);
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// we have an OK connection...
				InputStream rbody = connection.getInputStream();
				FileOutputStream fileoutstream = new FileOutputStream("/download" + url);
				IOUtils.copy(rbody, fileoutstream);
			} else {
				// error
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Object doPost(String urlPath, Object sentdata) {
		Object response = null;
		
		try {
			URL url = new URL(URL_PREFIX + urlPath);
			System.out.println("URL location = " + URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();
			xmlstream.toXML(sentdata, connection.getOutputStream());
			connection.getOutputStream().close();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// It worked.
				response = xmlstream.fromXML(connection.getInputStream());
			} else {
				// it wasnt OK...
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * This function takes in file lists and downloads each one
	 * 
	 * @param filelist
	 */
	private void downloadFiles(String filelist) {
		// take the string and parse on newliness
		String[] files = filelist.split("\\n");
		for (String file : files) {
			URL url;
			try {
				url = new URL(file);
				doGet(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
