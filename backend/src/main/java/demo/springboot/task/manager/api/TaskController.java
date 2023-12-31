package demo.springboot.task.manager.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import demo.springboot.task.manager.app.TaskApp;
import demo.springboot.task.manager.model.TaskCreateRequest;
import demo.springboot.task.manager.model.TaskDetailsResponse;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

	private TaskApp taskService;

	public TaskController(TaskApp taskService) {
		this.taskService = taskService;
	}

	@PostMapping("/create")
	public Map<String, String> creaetNewTask(@RequestBody TaskCreateRequest request) {
		return taskService.createNewTask(request);
	}

	@GetMapping("")
	public List<TaskDetailsResponse> getAllTasks() {
		return taskService.getAllTasks();
	}

	@GetMapping("/uid/{uid}/details")
	public TaskDetailsResponse getTaskByUid(@PathVariable String uid) {
		return taskService.getTaskInfoByUid(uid);
	}

	@GetMapping("/uid/{uid}/progress")
	public SseEmitter eventEmitter(@PathVariable String uid) throws IOException {
		return taskService.createEmitterByTaskUid(uid);
	}

}
