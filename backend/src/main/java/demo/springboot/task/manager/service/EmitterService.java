package demo.springboot.task.manager.service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class EmitterService {

	private final Map<UUID, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

	public SseEmitter createEmitter(UUID uuid) {
		SseEmitter sseEmitter = buildEmitter(uuid);
		sseEmitters.put(uuid, sseEmitter);
		sendUid(uuid, sseEmitter);
		return sseEmitter;
	}

	private void sendUid(UUID uuid, SseEmitter sseEmitter) {
		try {
			sseEmitter.send(uuid.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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

	public Optional<SseEmitter> getEmitterByTaskUid(UUID uid) {
		return Optional.ofNullable(sseEmitters.get(uid));
	}

}
