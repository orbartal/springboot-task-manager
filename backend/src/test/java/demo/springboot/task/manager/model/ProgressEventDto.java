package demo.springboot.task.manager.model;

public class ProgressEventDto {

	private String taskId;
	private String eventId;
	private String progress;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	@Override
	public String toString() {
		return "ProgressEventDto [taskId=" + taskId + ", eventId=" + eventId + ", progress=" + progress + "]";
	}

}
