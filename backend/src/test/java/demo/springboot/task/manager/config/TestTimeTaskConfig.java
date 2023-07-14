package demo.springboot.task.manager.config;

public class TestTimeTaskConfig {

	public static TestTimeTaskConfigBuilder builder() {
		return new TestTimeTaskConfigBuilder();
	}

	private final int numberOfTasks;
	private final int numberOfListnersPerTasks;
	private final int intervalInSeconds;
	private final int numberOfStages;
	private final int waitTimeInSecond;

	public TestTimeTaskConfig(int numberOfTasks, int numberOfTasksListners, int intervalInSeconds, int numberOfStages, int waitTimeInSecond) {
		this.numberOfTasks = numberOfTasks;
		this.numberOfListnersPerTasks = numberOfTasksListners;
		this.intervalInSeconds = intervalInSeconds;
		this.numberOfStages = numberOfStages;
		this.waitTimeInSecond = waitTimeInSecond;
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
