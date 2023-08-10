package demo.springboot.task.manager.test;

import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
import demo.springboot.task.manager.model.TaskStatusEnum;
import demo.springboot.task.manager.model.TimeTaskRequest;
import demo.springboot.task.manager.utils.RandomTextUtil;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskStatusTest {

	private static Optional<String> taskUid = Optional.empty();
	private static Optional<String> taskName = Optional.empty();

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
		String taskNameInput = RandomTextUtil.getRandomAlphabeticText(5);
		Response response = targetApi.createTask(taskNameInput);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		Assertions.assertNotNull(response.getBody());

		@SuppressWarnings("rawtypes")
		Map responseMap = response.getBody().as(Map.class);
		Assertions.assertNotNull(responseMap.get("uid"));
		taskUid = Optional.of(responseMap.get("uid").toString());
		taskName = Optional.of(taskNameInput);
	}
	
	@SuppressWarnings({"rawtypes" })
	@Order(2)
	@Test
	public void test02GetTaskByUid() throws Exception {
		String taskUidInput = taskUid.get();
		Response response = targetApi.getTaskByUid(taskUidInput);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		Assertions.assertNotNull(response.getBody());

		Map responseTask = response.getBody().as(Map.class);
		Assertions.assertNotNull(response.getBody());
		String taskUidOutput = responseTask.get("uid").toString();
		Assertions.assertEquals(taskUidInput, taskUidOutput);

		String actualTaskName = responseTask.get("name").toString();
		String expectedTaskName = taskName.get();
		Assertions.assertEquals(expectedTaskName, actualTaskName);

		String actualTaskStatus= responseTask.get("status").toString();
		String expectedTaskStatus = TaskStatusEnum.CREATED.name();
		Assertions.assertEquals(expectedTaskStatus, actualTaskStatus);
	}

	@Order(3)
	@Test
	public void test03StartTimeTask() throws Exception {
		TimeTaskRequest request = new TimeTaskRequest();
		request.setTaskUid(taskUid.get());
		request.setInterval(1);
		request.setRepeats(3);

		Response response = targetApi.startTimeTask(request);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		Assertions.assertNotNull(response.getBody());
	}

	@SuppressWarnings({"rawtypes" })
	@Order(4)
	@Test
	public void test04GetTaskByUid() throws Exception {
		String taskUidInput = taskUid.get();
		Response response = targetApi.getTaskByUid(taskUidInput);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		Assertions.assertNotNull(response.getBody());

		Map responseTask = response.getBody().as(Map.class);
		Assertions.assertNotNull(response.getBody());
		String taskUidOutput = responseTask.get("uid").toString();
		Assertions.assertEquals(taskUidInput, taskUidOutput);

		String actualTaskName = responseTask.get("name").toString();
		String expectedTaskName = taskName.get();
		Assertions.assertEquals(expectedTaskName, actualTaskName);

		String actualTaskStatus= responseTask.get("status").toString();
		String expectedTaskStatus = TaskStatusEnum.RUNNING.name();
		Assertions.assertEquals(expectedTaskStatus, actualTaskStatus);
	}

	@Order(5)
	@Test
	public void test05WaitUntilTaskStop() throws Exception {
		TimeUnit.SECONDS.sleep(5);
	}

	@SuppressWarnings("rawtypes")
	@Order(6)
	@Test
	public void test06GetTaskByUid() throws Exception {
		String taskUidInput = taskUid.get();
		Response response = targetApi.getTaskByUid(taskUidInput);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		Assertions.assertNotNull(response.getBody());

		Map responseTask = response.getBody().as(Map.class);
		Assertions.assertNotNull(response.getBody());
		String taskUidOutput = responseTask.get("uid").toString();
		Assertions.assertEquals(taskUidInput, taskUidOutput);

		String actualTaskName = responseTask.get("name").toString();
		String expectedTaskName = taskName.get();
		Assertions.assertEquals(expectedTaskName, actualTaskName);

		String actualTaskStatus= responseTask.get("status").toString();
		String expectedTaskStatus = TaskStatusEnum.END.name();
		Assertions.assertEquals(expectedTaskStatus, actualTaskStatus);
	}

}