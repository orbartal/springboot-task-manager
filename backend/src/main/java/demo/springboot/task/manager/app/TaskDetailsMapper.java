package demo.springboot.task.manager.app;

import org.springframework.stereotype.Component;

import demo.springboot.task.manager.model.TaskDetailsResponse;
import demo.springboot.task.manager.model.TaskInfo;

@Component
public class TaskDetailsMapper {

	public TaskDetailsResponse toResponse(TaskInfo info) {
		TaskDetailsResponse response = new TaskDetailsResponse();
		response.setName(info.getName());
		response.setUid(info.getUid().toString());
		return response;
	}

}
