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

	public SseEmitter createEmitter(UUID uuid) {
		SseEmitter sseEmitter = buildEmitter(uuid);
		TaskEmitter taskEmitter = new TaskEmitter(uuid, sseEmitter);
		sseEmitters.put(uuid, taskEmitter);
		taskEmitter.sendProgress(0.0);
		return sseEmitter;
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
