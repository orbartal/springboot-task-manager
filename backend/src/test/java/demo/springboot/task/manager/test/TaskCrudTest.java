package demo.springboot.task.manager.test;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import demo.springboot.task.manager.api.TargetApi;
import demo.springboot.task.manager.config.TestTimeTaskConfig;
import demo.springboot.task.manager.utils.TaskProgressDataFactory;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskCrudTest {

	private static final TestTimeTaskConfig testConfig = TestTimeTaskConfig.builder()
										.numberOfTasks(3)
										.numberOfListnersPerTasks(3)
										.waitTimeInSecond(15)
										.intervalInSeconds(1)
										.numberOfStages(5)
										.mapOfValueById(TaskProgressDataFactory.buildMarixOfProgressByEventId(0.0, 0.2, 0.4, 0.6, 0.8))
										.build();

	private static List<String> taskUids = new ArrayList<>();

	@LocalServerPort
	private int port;
	
	private static TargetApi targetApi;

	@Order(0)
	@Test
	public void test00GetTargetPort() {
		Assertions.assertNotEquals(0, port);
		targetApi = new TargetApi(port);
	}
	
	@Order(1)
	@Test
	public void test01CreateNewTaskAndGetItsUid() throws Exception {
		for (int i = 0; i < testConfig.getNumberOfTasks(); i++) {
			Response response = targetApi.createTask();

			Assertions.assertNotNull(response);
			Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
			Assertions.assertNotNull(response.getBody());

			@SuppressWarnings("rawtypes")
			Map responseMap = response.getBody().as(Map.class);
			Assertions.assertNotNull(responseMap.get("uid"));
			String taskUid = responseMap.get("uid").toString();
			taskUids.add(taskUid);
		}
		taskUids = taskUids.stream().sorted().toList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Order(2)
	@Test
	public void testGetAllTasks() throws Exception {
		Response response = targetApi.getAllTasks();

		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		Assertions.assertNotNull(response.getBody());

		List responseTasks = response.getBody().as(List.class);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(testConfig.getNumberOfTasks(), responseTasks.size());

		List<Map> actualTaskUids1 = responseTasks.stream().map(e->(HashMap)e).toList();
		List<String> actualTaskUids2 = actualTaskUids1.stream().map(m->m.get("uid").toString()).sorted().toList();

		System.out.println("TaskCrudTest.responseTasks");
		System.out.println(actualTaskUids2);
		Assertions.assertEquals(taskUids, actualTaskUids2);
	}

}