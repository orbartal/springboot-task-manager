package demo.springboot.task.manager.utils;

public class TargetUrlFactory {

	public static String buildCreateTaskUrl(int serverPort) {
		return "http://localhost:" + serverPort + "/api/v1/task/create";
	}

	public static String buildGetAllTasksUrl(int serverPort) {
		return "http://localhost:" + serverPort + "/api/v1/task";
	}

	public static String buildGetTaskByUidUrl(int serverPort, String taskUid) {
		return "http://localhost:" + serverPort + "/api/v1/task/uid/" + taskUid + "/details";
	}

	public static String buildStartTimeTaskUrl(int serverPort) {
		return "http://localhost:" + serverPort + "/api/v1/time/task";
	}

	public static String buildGetProgressUrl(int serverPort, String taskUid) {
		return "http://localhost:" + serverPort + "/api/v1/task/uid/" + taskUid + "/progress";
	}

}
