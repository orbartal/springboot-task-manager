package demo.springboot.task.manager.api;

import demo.springboot.task.manager.model.TimeTaskRequest;
import demo.springboot.task.manager.utils.TargetUrlFactory;
import io.restassured.response.Response;

public class TargetApi {

	private final int port;

	public TargetApi(int port) {
		this.port = port;
	}

	public Response createTask() {
		String url = TargetUrlFactory.buildCreateTaskUrl(port);
		return RestAssuredApi.createTask(url);
	}

	public Response startTimeTask(TimeTaskRequest request) {
		String url = TargetUrlFactory.buildStartTimeTaskUrl(port);
		return RestAssuredApi.startTimeTask(url, request);
	}

}
