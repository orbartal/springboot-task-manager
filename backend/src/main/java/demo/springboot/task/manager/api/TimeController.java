package demo.springboot.task.manager.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.springboot.task.manager.app.TimeTaskApp;
import demo.springboot.task.manager.model.TimeTaskRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/time")
public class TimeController {

	private final TimeTaskApp timeService;

	@Autowired
	public TimeController(TimeTaskApp timeService) {
		this.timeService = timeService;
	}

	@PostMapping("/task")
	public ResponseEntity<String> runTimeTask(@RequestBody TimeTaskRequest request) {
		try {
			timeService.startTimeTask(request);
			return ResponseEntity.status(HttpStatus.OK).body("");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
