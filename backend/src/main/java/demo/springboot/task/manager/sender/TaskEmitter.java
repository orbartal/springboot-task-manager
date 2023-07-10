package demo.springboot.task.manager.sender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

public class TaskEmitter {

	private final UUID taskUid;
	private final List<SseEmitter> emitters;
	private int counter;

	public TaskEmitter(UUID taskUid) {
		this.taskUid = taskUid;
		this.emitters = new ArrayList<>();
		this.counter = 0;
	}

	public void addEmitter(SseEmitter emitter) {
		emitters.add(emitter);
	}

	public synchronized void sendProgress(double progress) {
		counter ++;
		System.out.println("sendProgress: counter = " + counter + ", progress = " + progress);
		emitters.forEach(e->sendProgress(e, progress));
	}

	private void sendProgress(SseEmitter emitter, double progress) {
		try {
			SseEventBuilder eventBuilder = SseEmitter.event().id(counter + "").name(taskUid.toString()).data(progress);
			emitter.send(eventBuilder);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void completeWithError(Throwable t) {
		emitters.forEach(e->e.completeWithError(t));
	}

	public void complete() {
		emitters.forEach(e->e.complete());
	}

}
