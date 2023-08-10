package demo.springboot.task.manager.sender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.google.gson.JsonObject;

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
		emitters.forEach(e->sendProgress(e, progress));
	}

	private void sendProgress(SseEmitter emitter, double progress) {
		try {
			String jsonData = buildJsonData(progress);
			SseEventBuilder eventBuilder = SseEmitter.event().id(counter + "").name("progress").data(jsonData);
			emitter.send(eventBuilder);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String buildJsonData(double progress) {
		String eventCounter = counter+"";
		String taskId = taskUid.toString();
		String progressValue = progress + "";
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("taskId", taskId);
		jsonObject.addProperty("eventId", eventCounter);
		jsonObject.addProperty("progress", progressValue);
		String jsonData = jsonObject.toString();
		return jsonData;
	}

	public void completeWithError(Throwable t) {
		emitters.forEach(e->e.completeWithError(t));
	}

	public void complete() {
		emitters.forEach(e->e.complete());
	}

}
