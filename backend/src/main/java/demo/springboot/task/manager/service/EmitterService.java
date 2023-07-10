package demo.springboot.task.manager.service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import demo.springboot.task.manager.sender.TaskEmitter;

@Service
public class EmitterService {

	private final Map<UUID, TaskEmitter> sseEmitters = new ConcurrentHashMap<>();

	public void createEmitter(UUID uuid) {
		TaskEmitter taskEmitter = new TaskEmitter(uuid);
		sseEmitters.put(uuid, taskEmitter);
	}
	
	public Optional<SseEmitter> addEmitter(UUID taskUid) {
		TaskEmitter taskEmitter = sseEmitters.get(taskUid);
		if (taskEmitter == null) {
			return Optional.empty();
		}
		SseEmitter sseEmitter = buildEmitter(taskUid);
		taskEmitter.addEmitter(sseEmitter);
		return Optional.of(sseEmitter);
	}

	private SseEmitter buildEmitter(UUID uuid) {
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		sseEmitter.onCompletion(() -> sseEmitters.remove(uuid));
		sseEmitter.onTimeout(() -> sseEmitters.remove(uuid));
		return sseEmitter;
	}

	public void delete(UUID uid) {
		sseEmitters.remove(uid);
	}

	public Optional<TaskEmitter> getEmitterByTaskUid(UUID uid) {
		return Optional.ofNullable(sseEmitters.get(uid));
	}

}
