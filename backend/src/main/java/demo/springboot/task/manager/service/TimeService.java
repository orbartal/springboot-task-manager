package demo.springboot.task.manager.service;

import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Service;

import demo.springboot.task.manager.sender.TaskEmitter;
import demo.springboot.task.manager.subscriber.SubscriberEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

@Service
public class TimeService {

	private final TaskService taskService;

	public TimeService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void startTimeTask(UUID taskUid, long interval, int repeats) {
		TaskEmitter emitter = taskService.getEmitterByTaskUid(taskUid);
		SubscriberEmitter subscriber = new SubscriberEmitter(taskUid, emitter, repeats);
		Flux.interval(Duration.ofSeconds(1), Duration.ofSeconds(interval)).log().take(repeats).doFinally(e->onEnd(taskUid, e)).subscribe(subscriber);
	}

	private Object onEnd(UUID taskUid, SignalType e) {
		if (SignalType.ON_COMPLETE.equals(e)) {
			taskService.endTask(taskUid);
		}
		return null;
	}

}
