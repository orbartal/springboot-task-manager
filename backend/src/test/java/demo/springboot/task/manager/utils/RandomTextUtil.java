package demo.springboot.task.manager.utils;

import java.util.Random;

public class RandomTextUtil {

	public static String getRandomAlphabeticText(int size) {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String result = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return result;
	}

}
