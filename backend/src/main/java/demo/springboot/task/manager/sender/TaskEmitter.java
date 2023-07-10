package demo.springboot.task.manager.sender;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

public class TaskEmitter {

	private final UUID taskUid;
	private final SseEmitter emitter;
	private int counter;

	public TaskEmitter(UUID taskUid, SseEmitter emitter) {
		this.taskUid = taskUid;
		this.emitter = emitter;
		this.counter = 0;
	}
	
	public synchronized void sendProgress(double progress) {
		try {
			SseEventBuilder eventBuilder = SseEmitter.event().id(counter + "").name(taskUid.toString()).data(progress);
			emitter.send(eventBuilder);
			counter ++;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void completeWithError(Throwable t) {
		emitter.completeWithError(t);
	}

	public void complete() {
		emitter.complete();
	}

	public SseEmitter getEmitter() {
		return emitter;
	}

}
