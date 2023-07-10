package demo.springboot.task.manager.subscriber;

import java.util.UUID;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import demo.springboot.task.manager.sender.TaskEmitter;

public class SubscriberEmitter implements Subscriber<Long> {

	private final TaskEmitter emitter;
	private final long total;

	public SubscriberEmitter(UUID taskUid, TaskEmitter emitter, long total) {
		this.emitter = emitter;
		this.total = total;
	}

	@Override
	public void onSubscribe(Subscription s) {
		s.request(Long.MAX_VALUE);
	}

	@Override
	public void onNext(Long count) {
		double percentage = ((double) count / (double) total);
		emitter.sendProgress(percentage);
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
