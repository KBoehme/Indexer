package servertester.controllers;

import java.util.*;

import client.ClientCommunicator;
import servertester.views.*;
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
import shared.model.Field;
import shared.model.Record;
import shared.model.Value;

public class Controller implements IController {

	private IView _view;
	boolean first_run = true;
	ClientCommunicator cc;

	public Controller() {
		return;
	}

	public IView getView() {
		return _view;
	}

	public void setView(IView value) {
		_view = value;
	}

	// IController methods
	//

	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");

		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}

		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}

	private void validateUser() {
		String[] params = _view.getParameterValues();
		String host = _view.getHost();
		int port = Integer.valueOf(_view.getPort());

		cc = new ClientCommunicator(host, port);

		// ok time to validate the output here.
		ValidateUser_param vup = new ValidateUser_param();
		ValidateUser_result result = null;
		try {
			vup.setUsername(params[0]);
			vup.setPassword(params[1]);
			result = cc.validateUser(vup);
			if (result == null) {
				result = new ValidateUser_result();
				result.setSuccess(3);
			}
		} catch (Exception e) {
			result = new ValidateUser_result();
			result.setSuccess(3);
			_view.setResponse(result.getResultstring());
		}

		// _view.setRequest();
		_view.setResponse(result.getResultstring());
	}

	private void getProjects() {
		String[] params = _view.getParameterValues();
		String host = _view.getHost();
		int port = Integer.valueOf(_view.getPort());

		cc = new ClientCommunicator(host, port);

		// ok time to validate the output here.
		GetProjects_param gpp = new GetProjects_param();
		GetProjects_result result = null;

		try {
			gpp.setUsername(params[0]);
			gpp.setPassword(params[1]);
			result = cc.getProjects(gpp);
			if (result == null) {
				result = new GetProjects_result();
				result.setSuccess(2);
			}

		} catch (Exception e) {
			result = new GetProjects_result();
			result.setSuccess(2);
			_view.setResponse(result.getResultstring());
		}

		_view.setResponse(result.getResultstring());
	}

	private void getSampleImage() {
		String[] params = _view.getParameterValues();
		String host = _view.getHost();
		int port = Integer.valueOf(_view.getPort());

		cc = new ClientCommunicator(host, port);

		// ok time to validate the output here.
		GetSampleImage_param gsi = new GetSampleImage_param();
		GetSampleImage_result result = null;

		try {
			gsi.setUsername(params[0]);
			gsi.setPassword(params[1]);
			gsi.setProjectid(Integer.valueOf(params[2]));
			result = cc.getSampleImage(gsi);
			if (result == null) {
				result = new GetSampleImage_result();
				result.setSuccess(2);
			}
		} catch (Exception e) {
			result = new GetSampleImage_result();
			result.setSuccess(2);
			_view.setResponse(result.getResultstring());
		}

		_view.setResponse(result.getResultstring());
	}

	private void downloadBatch() {
		String[] params = _view.getParameterValues();
		String host = _view.getHost();
		int port = Integer.valueOf(_view.getPort());

		cc = new ClientCommunicator(host, port);

		// ok time to validate the output here.
		DownloadBatch_param dbp = new DownloadBatch_param();
		DownloadBatch_result result = null;

		try {
			dbp.setUsername(params[0]);
			dbp.setPassword(params[1]);
			dbp.setProjectid(Integer.valueOf(params[2]));
			result = cc.downloadBatch(dbp);
			if (result == null) {
				result = new DownloadBatch_result();
				result.setSuccess(2);
			} else {
				// lets append the necessary info for the helphtmls..
			}
		} catch (Exception e) {
			result = new DownloadBatch_result();
			result.setSuccess(2);
			_view.setResponse(result.getResultstring(null, null));
		}

		_view.setResponse(result.getResultstring(host, String.valueOf(port)));

	}

	private void submitBatch() {
		String[] params = _view.getParameterValues();
		String host = _view.getHost();
		int port = Integer.valueOf(_view.getPort());

		cc = new ClientCommunicator(host, port);

		// ok time to validate the output here.
		SubmitBatch_param sbp = new SubmitBatch_param();
		SubmitBatch_result result = null;

		// Jones,Fred,13;Rogers,Susan,42;,,;,,;VanFleet,Bill,23â€�
		ArrayList<Record> record_list = new ArrayList<Record>();

		try {
			sbp.setUsername(params[0]);
			sbp.setPassword(params[1]);
			sbp.setImageid(Integer.valueOf(params[2]));
			sbp.setRecord_values(record_list);
			String[] records = params[3].split(";");
			for (int k = 0; k < records.length; k++) { // k is the row number
				Record record = new Record(-1, k + 1, sbp.getImageid(), true);
				ArrayList<Value> value_list = new ArrayList<Value>();
				String[] values = records[k].split(",", -1);
				for (int i = 0; i < values.length; i++) { // i is the field
															// number.
					System.out.println("value: " + values[i]);
					// public Value(int iD, String value, int fieldnum, int
					// rownum, int imageID, int recordID) {
					Value v = new Value(-1, values[i], i + 1, k + 1, -1, -1);
					value_list.add(v);
				}
				record.setValues(value_list);
				record_list.add(record);
			}
			sbp.setRecord_values(record_list);
			result = cc.submitBatch(sbp);
			if (result == null) {
				result = new SubmitBatch_result();
				result.setSuccess(2);
			}
		} catch (Exception e) {
			result = new SubmitBatch_result();
			result.setSuccess(2);
			_view.setResponse(result.getResultstring());
		}

		_view.setResponse(result.getResultstring());
	}

	private void getFields() {
		String[] params = _view.getParameterValues();
		String host = _view.getHost();
		int port = Integer.valueOf(_view.getPort());

		cc = new ClientCommunicator(host, port);

		// ok time to validate the output here.
		GetFields_param gpp = new GetFields_param();
		GetFields_result result = null;

		try {
			gpp.setUsername(params[0]);
			gpp.setPassword(params[1]);
			if (params[2].isEmpty()) {
				System.out.println("hey");
				gpp.setProjectid(0);
			} else
				gpp.setProjectid(Integer.valueOf(params[2]));
			result = cc.getFields(gpp);
			if (result == null) {
				result = new GetFields_result();
				result.setSuccess(2);
			}
		} catch (Exception e) {
			result = new GetFields_result();
			result.setSuccess(2);
			_view.setResponse(result.getResultstring());
		}

		_view.setResponse(result.getResultstring());

	}

	private void search() {
		String[] params = _view.getParameterValues();
		String host = _view.getHost();
		int port = Integer.valueOf(_view.getPort());

		cc = new ClientCommunicator(host, port);

		// ok time to validate the output here.
		Search_param s_param = new Search_param();
		Search_result result = null;

		try {
			s_param.setUsername(params[0]);
			s_param.setPassword(params[1]);
			ArrayList<Integer> fields = new ArrayList<Integer>();
			ArrayList<String> search_values = new ArrayList<String>();
			String[] field_split = params[2].split(",");
			String[] search_split = params[3].split(",");

			for (String field : field_split) {
				int fid = Integer.valueOf(field);
				fields.add(fid);
			}
			for (String search : search_split) {
				search_values.add(search);
			}
			s_param.setFields(fields);
			s_param.setSearch_values(search_values);

			result = cc.search(s_param);
			if (result == null) {
				result = new Search_result();
				result.setSuccess(2);
			}
		} catch (Exception e) {
			result = new Search_result();
			result.setSuccess(2);
			_view.setResponse(result.getResultstring());
		}

		_view.setResponse(result.getResultstring());

	}

}
