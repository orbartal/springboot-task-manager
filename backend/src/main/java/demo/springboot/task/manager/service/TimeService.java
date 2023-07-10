package demo.springboot.task.manager.service;

import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import demo.springboot.task.manager.model.TimeTaskRequest;
import demo.springboot.task.manager.subscriber.SubscriberEmitter;
import reactor.core.publisher.Flux;

@Service
public class TimeService {

	private final TaskService taskService;

	public TimeService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void startTimeTask(TimeTaskRequest request) {
		UUID taskUid = UUID.fromString(request.getTaskUid());
		long interval = request.getInterval();
		int repeats = request.getRepeats();

		SseEmitter emitter = taskService.getEmitterByTaskUid(taskUid);
		SubscriberEmitter subscriber = new SubscriberEmitter(taskUid, emitter, repeats);

		Flux.interval(Duration.ZERO, Duration.ofSeconds(interval)).log().take(repeats).subscribe(subscriber);
	}

}
