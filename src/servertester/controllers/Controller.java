package servertester.controllers;

import java.util.*;

import client.ClientCommunicator;
import client.communicator.DownloadBatch_param;
import client.communicator.DownloadBatch_result;
import client.communicator.GetProjects_param;
import client.communicator.GetProjects_result;
import client.communicator.GetSampleImage_param;
import client.communicator.GetSampleImage_result;
import client.communicator.SubmitBatch_param;
import client.communicator.SubmitBatch_result;
import client.communicator.ValidateUser_param;
import client.communicator.ValidateUser_result;
import servertester.views.*;
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
		getView().setParameterNames(
				paramNames.toArray(new String[paramNames.size()]));
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

		if (first_run) {
			first_run = false;
			cc = new ClientCommunicator(host, port);
		}

		// ok time to validate the output here.
		ValidateUser_param vup = new ValidateUser_param();

		vup.setUsername(params[0]);
		vup.setPassword(params[1]);

		ValidateUser_result result = cc.validateUser(vup);
		//_view.setRequest();
		_view.setResponse(result.getResultstring());
	}

	private void getProjects() {
		String[] params = _view.getParameterValues();
		String host = _view.getHost();
		int port = Integer.valueOf(_view.getPort());

		if (first_run) {
			first_run = false;
			cc = new ClientCommunicator(host, port);
		}

		// ok time to validate the output here.
		GetProjects_param gpp = new GetProjects_param();

		gpp.setUsername(params[0]);
		gpp.setPassword(params[1]);

		GetProjects_result result = cc.getProjects(gpp);
		_view.setResponse(result.getResultstring());

	}

	private void getSampleImage() {
		String[] params = _view.getParameterValues();
		String host = _view.getHost();
		int port = Integer.valueOf(_view.getPort());

		if (first_run) {
			first_run = false;
			cc = new ClientCommunicator(host, port);
		}

		// ok time to validate the output here.
		GetSampleImage_param gsi = new GetSampleImage_param();

		gsi.setUsername(params[0]);
		gsi.setPassword(params[1]);
		gsi.setProjectid(Integer.valueOf(params[2]));

		GetSampleImage_result result = cc.getSampleImage(gsi);
		_view.setResponse(result.getResultstring());
	}

	private void downloadBatch() {
		String[] params = _view.getParameterValues();
		String host = _view.getHost();
		int port = Integer.valueOf(_view.getPort());

		if (first_run) {
			first_run = false;
			cc = new ClientCommunicator(host, port);
		}

		// ok time to validate the output here.
		DownloadBatch_param dbp = new DownloadBatch_param();

		dbp.setUsername(params[0]);
		dbp.setPassword(params[1]);
		dbp.setProjectid(Integer.valueOf(params[2]));

		DownloadBatch_result result = cc.downloadBatch(dbp);
		_view.setResponse(result.getResultstring());

	}

	private void getFields() {
		String[] params = _view.getParameterValues();
		String host = _view.getHost();
		int port = Integer.valueOf(_view.getPort());

		if (first_run) {
			first_run = false;
			cc = new ClientCommunicator(host, port);
		}

		// ok time to validate the output here.
		GetProjects_param gpp = new GetProjects_param();

		gpp.setUsername(params[0]);
		gpp.setPassword(params[1]);

		GetProjects_result result = cc.getProjects(gpp);
		_view.setResponse(result.getResultstring());

	}

	private void submitBatch() {
		String[] params = _view.getParameterValues();
		String host = _view.getHost();
		int port = Integer.valueOf(_view.getPort());

		if (first_run) {
			first_run = false;
			cc = new ClientCommunicator(host, port);
		}

		// ok time to validate the output here.
		SubmitBatch_param sbp = new SubmitBatch_param();

		sbp.setUsername(params[0]);
		sbp.setPassword(params[1]);
		sbp.setImageid(Integer.valueOf(params[2]));
		sbp.setImageid(Integer.valueOf(params[2]));

		ArrayList<Value> r_values = new ArrayList<Value>();

		// sbp.setRecord_values(record_values);

		SubmitBatch_result result = cc.submitBatch(sbp);
		_view.setResponse(result.getResultstring());

	}

	private void search() {
		String[] params = _view.getParameterValues();
		String host = _view.getHost();
		int port = Integer.valueOf(_view.getPort());

		if (first_run) {
			first_run = false;
			cc = new ClientCommunicator(host, port);
		}

		// ok time to validate the output here.
		GetProjects_param gpp = new GetProjects_param();

		gpp.setUsername(params[0]);
		gpp.setPassword(params[1]);

		GetProjects_result result = cc.getProjects(gpp);
		_view.setResponse(result.getResultstring());

	}

}
