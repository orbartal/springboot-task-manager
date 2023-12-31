package demo.springboot.task.manager.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import demo.springboot.task.manager.data.TaskData;
import demo.springboot.task.manager.model.TaskInfo;
import demo.springboot.task.manager.sender.TaskEmitter;

@Service
public class TaskService {

	private final TaskData taskData;

	private final EmitterService emitterService;

	public TaskService(TaskData taskData, EmitterService emitterService) {
		this.taskData = taskData;
		this.emitterService = emitterService;
	}

	public Map<String, String> createNewTask(String taskName) {
		UUID uid = UUID.randomUUID();
		TaskInfo taskInfo = new TaskInfo(uid, taskName);
		taskData.add(taskInfo);
		emitterService.createEmitter(uid);
		return Collections.singletonMap("uid", uid.toString());
	}

	public void startTask(UUID taskUid) {
		taskData.start(taskUid);
	}

	public void endTask(UUID uid) {
		taskData.end(uid);
		emitterService.delete(uid);
	}

	public TaskEmitter getEmitterByTaskUid(UUID uid) {
		Optional<TaskEmitter> emitter = emitterService.getEmitterByTaskUid(uid);
		if (!emitter.isPresent()) {
			throw new IllegalArgumentException("No task found with uid: " + uid);
		}
		return emitter.get();
	}

	public SseEmitter createEmitterByTaskUid(UUID uid) {
		Optional<SseEmitter> emitter = emitterService.addEmitter(uid);
		if (!emitter.isPresent()) {
			throw new IllegalArgumentException("No task found with uid: " + uid);
		}
		return emitter.get();
	}

	public TaskInfo getTaskInfoByUid(UUID uuid) {
		Optional<TaskInfo> opt = taskData.readInfoByUid(uuid);
		if (opt.isEmpty()) {
			throw new IllegalArgumentException("Not found task with uid: " + uuid);
		}
		return opt.get();
	}

	public List<TaskInfo> getAllTasks() {
		List<TaskInfo> tasks = taskData.readAll();
		return tasks;
	}

}
