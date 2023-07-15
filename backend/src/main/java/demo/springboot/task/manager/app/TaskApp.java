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
	private TaskValidator taskValidator;
	private TaskDetailsMapper taskMapper;

	public TaskApp(TaskService taskService, TaskValidator taskValidator, TaskDetailsMapper taskMapper) {
		this.taskService = taskService;
		this.taskValidator = taskValidator;
		this.taskMapper = taskMapper;
	}

	public Map<String, String> createNewTask(TaskCreateRequest request) {
		taskValidator.validate(request);
		return taskService.createNewTask(request.getName());
	}

	public List<TaskDetailsResponse> getAllTasks() {
		List<TaskInfo> infos = taskService.getAllTasks();
		return infos.stream().map(i->taskMapper.toResponse(i)).toList();
	}

	public TaskDetailsResponse getTaskInfoByUid(String uid) {
		UUID uuid = taskValidator.validateUid(uid);
		TaskInfo info = taskService.getTaskInfoByUid(uuid);
		return taskMapper.toResponse(info);
	}

	public SseEmitter createEmitterByTaskUid(String uid) {
		UUID uuid = taskValidator.validateUid(uid);
		return taskService.createEmitterByTaskUid(uuid);
	}

}
