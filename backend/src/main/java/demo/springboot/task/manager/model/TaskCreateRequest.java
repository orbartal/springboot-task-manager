package demo.springboot.task.manager.model;

public class TaskCreateRequest {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TaskCreateRequest [name=" + name + "]";
	}

}
