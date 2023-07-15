package demo.springboot.task.manager.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import demo.springboot.task.manager.model.TaskCreateRequest;
import demo.springboot.task.manager.model.TaskInfo;
import demo.springboot.task.manager.service.TaskService;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

	private TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@PostMapping("/create")
	public Map<String, String> creaetNewTask(@RequestBody TaskCreateRequest request) {
		return taskService.createNewTask(request.getName());
	}

	@GetMapping("")
	public List<TaskInfo> getAllTasks() {
		return taskService.getAllTasks();
	}

	@GetMapping("/uid/{uid}/details")
	public TaskInfo getAllTasks(@PathVariable String uid) {
		UUID uuid = UUID.fromString(uid);
		return taskService.getTaskInfoByUid(uuid);
	}

	@GetMapping("/uid/{uid}/progress")
	public SseEmitter eventEmitter(@PathVariable String uid) throws IOException {
		UUID uuid = UUID.fromString(uid);
		return taskService.createEmitterByTaskUid(uuid);
	}

}
