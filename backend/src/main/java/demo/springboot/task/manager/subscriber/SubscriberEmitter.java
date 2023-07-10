package demo.springboot.task.manager.subscriber;

import java.io.IOException;
import java.util.UUID;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

public class SubscriberEmitter implements Subscriber<Long> {

	private final UUID taskUid;
	private final SseEmitter emitter;
	private final long total;
	private int counter;

	public SubscriberEmitter(UUID taskUid, SseEmitter emitter, long total) {
		this.taskUid = taskUid;
		this.emitter = emitter;
		this.total = total;
		this.counter = 1;
	}

	@Override
	public void onSubscribe(Subscription s) {
		s.request(Long.MAX_VALUE);
	}

	@Override
	public void onNext(Long count) {
		try {
			double percentage = ((double) count / (double) total);
			SseEventBuilder eventBuilder = SseEmitter.event().id(counter + "").name(taskUid.toString())
					.data(percentage);
			emitter.send(eventBuilder);
			counter++;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onError(Throwable t) {
		emitter.completeWithError(t);
	}

	@Override
	public void onComplete() {
		emitter.complete();
	}

}
