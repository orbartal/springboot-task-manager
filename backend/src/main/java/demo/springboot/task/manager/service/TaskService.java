package demo.springboot.task.manager.service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class TaskService {

	private final EmitterService emitterService;

	public TaskService(EmitterService emitterService) {
		this.emitterService = emitterService;
	}

	public Map<String, String> createNewTask() {
		UUID uid = UUID.randomUUID();
		emitterService.createEmitter(uid);
		return Collections.singletonMap("uid", uid.toString());
	}
	
	public void endTask(UUID uid) {
		emitterService.delete(uid);
	}

	public SseEmitter getEmitterByTaskUid(UUID uid) {
		Optional<SseEmitter> emitter = emitterService.getEmitterByTaskUid(uid);
		if (!emitter.isPresent()) {
			throw new RuntimeException("No task found with uid: " + uid);
		}
		return emitter.get();
	}

}
