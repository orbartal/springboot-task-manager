package demo.springboot.task.manager.config;

import java.util.Map;

public class TestTimeTaskConfigBuilder {

	private int numberOfTasks;
	private int numberOfListnersPerTasks;
	private int waitTimeInSecond;
	private int intervalInSeconds;
	private int numberOfStages;
	private Map<Integer, Double> valueById;
	
	protected TestTimeTaskConfigBuilder () {}

	public TestTimeTaskConfigBuilder numberOfTasks(int numberOfTasks) {
		this.numberOfTasks = numberOfTasks;
		return this;
	}

	public TestTimeTaskConfigBuilder numberOfListnersPerTasks(int numberOfListnersPerTasks) {
		this.numberOfListnersPerTasks = numberOfListnersPerTasks;
		return this;
	}

	public TestTimeTaskConfigBuilder waitTimeInSecond(int waitTimeInSecond) {
		this.waitTimeInSecond = waitTimeInSecond;
		return this;
	}

	public TestTimeTaskConfigBuilder intervalInSeconds(int intervalInSeconds) {
		this.intervalInSeconds = intervalInSeconds;
		return this;
	}

	public TestTimeTaskConfigBuilder numberOfStages(int numberOfStages) {
		this.numberOfStages = numberOfStages;
		return this;
	}

	public TestTimeTaskConfigBuilder mapOfValueById(Map<Integer, Double> valueById) {
		this.valueById = valueById;
		return this;
	}

	public TestTimeTaskConfig build() {
		return new TestTimeTaskConfig(numberOfTasks, numberOfListnersPerTasks, intervalInSeconds, numberOfStages, waitTimeInSecond, valueById);
	}

}
