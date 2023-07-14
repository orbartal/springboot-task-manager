package demo.springboot.task.manager.model;

import java.util.UUID;

public class TaskInfo {
	
	private final UUID uid;
	private final String name;

	public TaskInfo(UUID uid, String name) {
		this.uid = uid;
		this.name = name;
	}

	public UUID getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "TaskInfo [uid=" + uid + ", name=" + name + "]";
	}

}
