package demo.springboot.task.manager.model;

public class TimeTaskRequest {

	private String taskUid;
	private long interval;
	private int repeats;

	public String getTaskUid() {
		return taskUid;
	}

	public void setTaskUid(String taskUid) {
		this.taskUid = taskUid;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public int getRepeats() {
		return repeats;
	}

	public void setRepeats(int repeats) {
		this.repeats = repeats;
	}

	@Override
	public String toString() {
		return "TimeTaskRequest [taskUid=" + taskUid + ", interval=" + interval + ", repeats=" + repeats + "]";
	}

}
