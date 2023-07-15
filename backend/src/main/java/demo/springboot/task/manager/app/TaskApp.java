package demo.springboot.task.manager.app;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import demo.springboot.task.manager.model.TaskInfo;
import demo.springboot.task.manager.service.TaskService;

@Service
public class TaskApp {

	private TaskService taskService;

	public TaskApp(TaskService taskService) {
		this.taskService = taskService;
	}

	public Map<String, String> createNewTask(String name) {
		return taskService.createNewTask(name);
	}

	public List<TaskInfo> getAllTasks() {
		return taskService.getAllTasks();
	}

	public TaskInfo getTaskInfoByUid(UUID uuid) {
		return taskService.getTaskInfoByUid(uuid);
	}

	public SseEmitter createEmitterByTaskUid(UUID uuid) {
		return taskService.createEmitterByTaskUid(uuid);
	}

}
