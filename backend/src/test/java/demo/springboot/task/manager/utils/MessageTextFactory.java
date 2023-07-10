package demo.springboot.task.manager.utils;

import java.time.LocalTime;

import org.springframework.http.codec.ServerSentEvent;

public class MessageTextFactory {

	public static String getMessage(ServerSentEvent<String> content) {
		StringBuilder sb = new StringBuilder();
		sb.append("time: " + LocalTime.now());
		sb.append("id: " + content.id());
		sb.append("event: " + content.event());
		sb.append("data: " + content.data());
		return sb.toString();
	}

}
