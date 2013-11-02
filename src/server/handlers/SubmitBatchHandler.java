/**
 * 
 */
package server.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.DownloadBatch_param;
import shared.communication.DownloadBatch_result;
import shared.communication.SubmitBatch_param;
import shared.communication.SubmitBatch_result;
import shared.model.Field;
import shared.model.Image;
import shared.model.Project;
import shared.model.Record;
import shared.model.User;
import shared.model.Value;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Kevin
 * 
 */
public class SubmitBatchHandler extends BaseHandler implements HttpHandler {

	/**
	 * 
	 */
	public SubmitBatchHandler() {
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		SubmitBatch_result result = new SubmitBatch_result();

		try {
			super.startHandler();

			SubmitBatch_param param = (SubmitBatch_param) xmlstream.fromXML(exchange.getRequestBody());
			exchange.getRequestBody().close();

			boolean valid_user = database.getValidateuserdao().validateUser(param.getUsername(), param.getPassword(),
					database);
			User user = new User();
			user = database.getUserdao().getUser(param.getUsername(), param.getPassword(), database);

			if (valid_user && user.getCurr_batch_id() == param.getImageid()) {

				Image image = database.getImagedao().getImage(param.getImageid(), database);
				Project project = database.getProjectdao().getProject(image.getProjectID(), database);
				int numfields = project.getNumfields();

				ArrayList<Record> records = new ArrayList<Record>();
				records = param.getRecord_values();

				for (Record record : records) {
					int record_id = database.getRecorddao().insert(record, database);
					ArrayList<Value> values = new ArrayList<Value>();
					values = record.getValues();

					if (values.size() != numfields) {
						super.sendError(result, exchange);
					}
					for (Value value : values) {
						value.setRecordID(record_id);
						database.getValuedao().insert(value, database);
					}
				}

				user.setCurr_batch_id(-1);
				int num_records = user.getNum_indexed_records() + 1;
				user.setNum_indexed_records(num_records);
				database.getUserdao().update(user, database);

				super.sendOK(result, exchange);

			} else {
				super.sendError(result, exchange);
			}

		} catch (Exception e) {
			super.sendError(result, exchange);
		}
	}
}
