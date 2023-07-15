package demo.springboot.task.manager.app;

import java.util.UUID;

import org.springframework.stereotype.Service;

import demo.springboot.task.manager.model.TimeTaskRequest;
import demo.springboot.task.manager.service.TimeService;

@Service
public class TimeTaskApp {

	private final TimeService timeService;
	private TaskValidator taskValidator;

	public TimeTaskApp(TimeService timeService, TaskValidator taskValidator) {
		this.timeService = timeService;
		this.taskValidator = taskValidator;
	}

	public void startTimeTask(TimeTaskRequest request) {
		UUID taskUid = taskValidator.validateUid(request.getTaskUid());
		long interval = request.getInterval();
		int repeats = request.getRepeats();
		timeService.startTimeTask(taskUid, interval, repeats);
		
	}
}
