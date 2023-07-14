package demo.springboot.task.manager.config;

public class TestTimeTaskConfig {

	private final int numberOfTasks; // NUMBER_OF_TASKS 3
	private final int numberOfListnersPerTasks; // NUMBER_OF_LISTNERS 3
	private final int intervalInSeconds; // TASK_STAGE_INTERVAL_LENGTH_IN_SECONDS 1
	private final int numberOfStages; // NUMBER_OF_STAGES_PER_TASK 5
	private final int waitTimeInSecond; // SECONDS_TO_WAIT_FOR_ALL_TASKS_TO_FINISH 15

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
