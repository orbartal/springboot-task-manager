package demo.springboot.task.manager.app;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import demo.springboot.task.manager.model.TaskCreateRequest;
import demo.springboot.task.manager.model.TaskDetailsResponse;
import demo.springboot.task.manager.model.TaskInfo;
import demo.springboot.task.manager.service.TaskService;

@Service
public class TaskApp {

	private TaskService taskService;

	public TaskApp(TaskService taskService) {
		this.taskService = taskService;
	}

	public Map<String, String> createNewTask(TaskCreateRequest request) {
		return taskService.createNewTask(request.getName());
	}

	public List<TaskInfo> getAllTasks() {
		return taskService.getAllTasks();
	}

	public TaskDetailsResponse getTaskInfoByUid(String uid) {
		UUID uuid = UUID.fromString(uid);
		TaskInfo info = taskService.getTaskInfoByUid(uuid);

		TaskDetailsResponse response = new TaskDetailsResponse();
		response.setName(info.getName());
		response.setUid(info.getUid().toString());
		return response;
	}

	public SseEmitter createEmitterByTaskUid(String uid) {
		UUID uuid = UUID.fromString(uid);
		return taskService.createEmitterByTaskUid(uuid);
	}

}
