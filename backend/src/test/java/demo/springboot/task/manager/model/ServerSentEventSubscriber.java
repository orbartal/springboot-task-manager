package demo.springboot.task.manager.model;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.http.codec.ServerSentEvent;

public class ServerSentEventSubscriber implements Subscriber<ServerSentEvent<String>> {

	private Subscription subscription;
	private final String testName;
	private final ProgressResult result;

	public ServerSentEventSubscriber(String testName) {
		this.testName = testName;
		this.result = new ProgressResult();
	}

	@Override
	public void onSubscribe(Subscription s) {
		subscription = s;
		subscription.request(1);
	}

	@Override
	public void onNext(ServerSentEvent<String> e) {
		subscription.request(1);
		this.result.onNext(e);
	}

	@Override
	public void onError(Throwable t) {
		this.result.onError(t);
	}

	@Override
	public void onComplete() {
		this.result.onComplete();
	}

	public ProgressResult getResult() {
		return result;
	}

	public String getTestName() {
		return testName;
	}

}
