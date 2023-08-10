package demo.springboot.task.manager.model;

import java.util.UUID;

public class TaskInfo {
	
	private TaskStatusEnum status;
	private final UUID uid;
	private final String name;

	public TaskInfo(UUID uid, String name) {
		this.status = TaskStatusEnum.CREATED;
		this.uid = uid;
		this.name = name;
	}

	public UUID getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public void setStatus(TaskStatusEnum status) {
		this.status = status;
	}

	public TaskStatusEnum getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "TaskInfo [status=" + status + ", uid=" + uid + ", name=" + name + "]";
	}

}
