package demo.springboot.task.manager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.codec.ServerSentEvent;

public class ProgressResult {
	
	private List<Map<String, String>> events = new ArrayList<>();
	private Optional<Throwable> error = Optional.empty();
	private boolean isCompleted = false; 

	public void onNext (ServerSentEvent<String> e) {
		Map<String, String> m = new HashMap<>();
		m.put("id", e.id());
		m.put("data", e.data());
		m.put("event", e.event());
		m.put("comment", e.comment());
		m.put("retry", e.retry()+"");
		events.add(m);
	}
	
	public void onError (Throwable t) {
		error = Optional.ofNullable(t);
	}
	
	public void onComplete() {
		isCompleted = true;
	}

	public List<Map<String, String>> getEvents() {
		return events;
	}

	public Optional<Throwable> getError() {
		return error;
	}
	
	public boolean getIsError() {
		return error.isPresent();
	}

	public boolean getIsCompleted() {
		return isCompleted;
	}

}
