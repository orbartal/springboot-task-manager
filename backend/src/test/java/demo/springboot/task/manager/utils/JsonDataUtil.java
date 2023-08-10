package demo.springboot.task.manager.utils;

import com.google.gson.Gson;

import demo.springboot.task.manager.model.ProgressEventDto;

public class JsonDataUtil {

	public static double getValueFromData(String input) {
		ProgressEventDto event = new Gson().fromJson(input, ProgressEventDto.class);
		String progressText = event.getProgress();
		return Double.parseDouble(progressText);
	}
}
