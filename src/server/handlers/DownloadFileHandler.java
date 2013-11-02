/**
 * 
 */
package server.handlers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author Kevin
 * 
 */
public class DownloadFileHandler implements HttpHandler {

	/**
	 * 
	 */
	public DownloadFileHandler() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		java.net.URI uri = exchange.getRequestURI();
		// users/guest/k/kboehme1/git/Indexer/Recordsimages/1890_image0.png
		String[] path = uri.getPath().split("/Download/");
		System.out.println(path[1]);
		File file = new File(path[1]);

		if (file.exists()) {
			byte[] bytearray = new byte[(int) file.length()];
			FileInputStream fstream = new FileInputStream(file);
			BufferedInputStream bstream = new BufferedInputStream(fstream);

			bstream.read(bytearray, 0, bytearray.length);

			exchange.sendResponseHeaders(200, file.length());

			OutputStream ostream = exchange.getResponseBody();
			ostream.write(bytearray, 0, bytearray.length);
			ostream.close();

			bstream.close();
		} else {
			File newfile = new File("NoFile.html");
			byte[] bytearray = new byte[(int) newfile.length()];
			FileInputStream fstream = new FileInputStream(newfile);
			BufferedInputStream bstream = new BufferedInputStream(fstream);

			bstream.read(bytearray, 0, bytearray.length);

			exchange.sendResponseHeaders(200, file.length());

			OutputStream ostream = exchange.getResponseBody();
			ostream.write(bytearray, 0, bytearray.length);
			ostream.close();

			bstream.close();
		}
	}
}
