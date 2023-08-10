package demo.springboot.task.manager.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

	public static Map<Integer, Double> buildMarixOfProgressByEventId(Double... values) {
		List<Double> values2 = Stream.of(values).toList();
		return buildMarixOfProgressByEventId(values2);
	}

	public static Map<Integer, Double> buildMarixOfProgressByEventId(List<Double> values) {
		return IntStream.range(0, values.size()).boxed().collect(Collectors.toMap(i -> i+1, i -> values.get(i)));
	}

	public static  Map<Integer, Double> buildMarixOfProgressByEventId(ProgressResult progressResult) {
		return progressResult.getEvents().stream()
				.collect(Collectors.toMap(
						e -> Integer.parseInt(e.get("id")), 
						e -> JsonDataUtil.getValueFromData(e.get("data"))
				));
	}
}
