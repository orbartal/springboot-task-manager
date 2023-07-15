package demo.springboot.task.manager.model;

public class TaskDetailsResponse {
	
	private String uid;
	private String name;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TaskDetailsResponse [uid=" + uid + ", name=" + name + "]";
	}

}
