package demo.springboot.task.manager.config;

import java.util.List;
import java.util.Map;

public class TestTimeTaskConfig {

	public static TestTimeTaskConfigBuilder builder() {
		return new TestTimeTaskConfigBuilder();
	}

	private final int numberOfTasks;
	private final int numberOfListnersPerTasks;
	private final int intervalInSeconds;
	private final int numberOfStages;
	private final int waitTimeInSecond;
	private final Map<Integer, Double> valueById;

	public TestTimeTaskConfig(int numberOfTasks, int numberOfTasksListners, int intervalInSeconds, int numberOfStages, int waitTimeInSecond, Map<Integer, Double> valueById) {
		this.numberOfTasks = numberOfTasks;
		this.numberOfListnersPerTasks = numberOfTasksListners;
		this.intervalInSeconds = intervalInSeconds;
		this.numberOfStages = numberOfStages;
		this.waitTimeInSecond = waitTimeInSecond;
		this.valueById = valueById;
	}

	public int getNumberOfTasks() {
		return numberOfTasks;
	}

	public int getNumberOfListnersPerTask() {
		return numberOfListnersPerTasks;
	}

	public int getIntervalInSeconds() {
		return intervalInSeconds;
	}

	public int getNumberOfStages() {
		return numberOfStages;
	}

	public int getWaitTimeInSecond() {
		return waitTimeInSecond;
	}

	public Map<Integer, Double> getMapOfValueById() {
		return valueById;
	}

	public List<Integer> getEventsIds() {
		return valueById.keySet().stream().sorted().toList();
	}

	@Override
	public String toString() {
		return "TestTimeTaskConfig ["
				+ "numberOfTasks=" + numberOfTasks +
				", numberOfListnersPerTasks=" + numberOfListnersPerTasks + 
				", intervalInSeconds=" + intervalInSeconds + 
				", numberOfStages=" + numberOfStages + 
				", waitTimeInSecond=" + waitTimeInSecond + 
				"]";
	}

}
