package demo.springboot.task.manager.utils;

import org.json.JSONException;
import org.json.JSONObject;

import demo.springboot.task.manager.model.TaskCreateRequest;
import demo.springboot.task.manager.model.TimeTaskRequest;

public class JsonMapper {
	
	public static JSONObject toJson(TimeTaskRequest request) {
		try {
			return new JSONObject()
			         .put("taskUid",request.getTaskUid())
			         .put("interval",request.getInterval()+"")
			         .put("repeats", request.getRepeats()+"");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public static JSONObject toJson(TaskCreateRequest request) {
		try {
			return new JSONObject()
			         .put("name",request.getName());
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

}
