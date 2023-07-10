package demo.springboot.task.manager.api;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

	@GetMapping("")
	public ResponseEntity<?> getHello() throws IOException {
		return ResponseEntity.ok("hello");

	}

}