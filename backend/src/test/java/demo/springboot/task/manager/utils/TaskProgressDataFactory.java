package demo.springboot.task.manager.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import demo.springboot.task.manager.model.ProgressResult;

public class TaskProgressDataFactory {

	public static Map<Integer, Double> buildMarixOfProgressByEventId() {
		
		@SuppressWarnings("serial")
		Map<Integer, Double> progressByEventId = new HashMap<Integer, Double>() {
			{
				put(1, 0.0);
				put(2, 0.2);
				put(3, 0.4);
				put(4, 0.6);
				put(5, 0.8);
			}
		};
		return progressByEventId;
	}
	
	public static  Map<Integer, Double> buildMarixOfProgressByEventId(ProgressResult progressResult) {
		return progressResult.getEvents().stream()
				.collect(Collectors.toMap(e -> Integer.parseInt(e.get("id")), e -> Double.parseDouble(e.get("data"))));
	}
}
