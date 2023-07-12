package demo.springboot.task.manager.utils;

import java.util.HashMap;
import java.util.Map;

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
}
