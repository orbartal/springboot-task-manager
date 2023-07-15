package demo.springboot.task.manager.api;

import org.json.JSONObject;

import demo.springboot.task.manager.model.TaskCreateRequest;
import demo.springboot.task.manager.model.TimeTaskRequest;
import demo.springboot.task.manager.utils.JsonMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredApi {

	public static Response createTask(String url, TaskCreateRequest request) {
		JSONObject json = JsonMapper.toJson(request);
		return RestAssured.given().contentType("application/json").body(json.toString()).when().post(url).andReturn();
	}

	public static Response startTimeTask(String url, TimeTaskRequest request) {
		JSONObject json = JsonMapper.toJson(request);
		return RestAssured.given().contentType("application/json").body(json.toString()).when().post(url).andReturn();
	}

	public static Response getAllTasks(String url) {
		return RestAssured.given().contentType("application/json").when().get(url).andReturn();
	}

	public static Response getTaskByUid(String url) {
		return RestAssured.given().contentType("application/json").when().get(url).andReturn();
	}

	public static Response getProgress(String url) {
		String contentType = "text/event-stream;charset=UTF-8";
		return RestAssured.given().contentType(contentType).when().get(url).andReturn();
	}

}
