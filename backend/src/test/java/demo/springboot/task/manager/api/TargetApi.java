package demo.springboot.task.manager.api;

import demo.springboot.task.manager.model.TaskCreateRequest;
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
		TaskCreateRequest request = new TaskCreateRequest();
		request.setName("NoName");
		return RestAssuredApi.createTask(url, request);
	}
	
	public Response createTask(TaskCreateRequest request) {
		String url = TargetUrlFactory.buildCreateTaskUrl(port);
		return RestAssuredApi.createTask(url, request);
	}

	public Response startTimeTask(TimeTaskRequest request) {
		String url = TargetUrlFactory.buildStartTimeTaskUrl(port);
		return RestAssuredApi.startTimeTask(url, request);
	}

	public Response getAllTasks() {
		String url = TargetUrlFactory.buildGetAllTasksUrl(port);
		return RestAssuredApi.getAllTasks(url);
	}

	public Response getTaskByUid(String taskUid) {
		String url = TargetUrlFactory.buildGetTaskByUidUrl(port, taskUid);
		return RestAssuredApi.getTaskByUid(url);
	}

}
