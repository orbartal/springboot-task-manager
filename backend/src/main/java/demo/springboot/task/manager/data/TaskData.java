package demo.springboot.task.manager.data;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import demo.springboot.task.manager.model.TaskInfo;

@Component
public class TaskData {

	private final Map<UUID, TaskInfo> tasks = new ConcurrentHashMap<>();

	public void add(TaskInfo taskInfo) {
		tasks.put(taskInfo.getUid(), taskInfo);
	}

	public void end(UUID uid) {
		tasks.remove(uid);
	}

	public Optional<TaskInfo> readInfoByUid(UUID uuid) {
		return Optional.ofNullable(tasks.get(uuid));
	}

	public List<TaskInfo> readAll() {
		return tasks.values().stream().toList();
	}

}
