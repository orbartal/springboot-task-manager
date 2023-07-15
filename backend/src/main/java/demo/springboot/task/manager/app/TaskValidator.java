package demo.springboot.task.manager.app;

import java.util.UUID;

import org.springframework.stereotype.Component;

import demo.springboot.task.manager.model.TaskCreateRequest;

@Component
public class TaskValidator {

	public void validate(TaskCreateRequest request) {
		String taskName = request.getName();
		if (taskName == null || taskName.isBlank()) {
			throw new IllegalArgumentException("Missing task name");
		}
	}

	public UUID validateUid(String uid) {
		try {
			return UUID.fromString(uid);
		} catch (Throwable t) {
			throw new IllegalArgumentException("Invalid taks uid: " + uid);
		}
	}

}
