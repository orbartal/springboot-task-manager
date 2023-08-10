package demo.springboot.task.manager.test;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import demo.springboot.task.manager.api.TargetApi;
import demo.springboot.task.manager.model.ProgressResult;
import demo.springboot.task.manager.model.ServerSentEventSubscriber;
import demo.springboot.task.manager.model.TimeTaskRequest;
import demo.springboot.task.manager.utils.RandomTextUtil;
import demo.springboot.task.manager.utils.TargetUrlFactory;
import demo.springboot.task.manager.utils.TaskProgressDataFactory;
import io.restassured.response.Response;
import reactor.core.publisher.Flux;


@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ManyTasksOneListenerTest {

	private static final int NUMBER_OF_TASKS = 10;
	private static List<String> taskUids = new ArrayList<>();
	private static List<ProgressResult> results  = new ArrayList<>();

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
		for (int i = 0; i < NUMBER_OF_TASKS; i++) {
			String taskName = RandomTextUtil.getRandomAlphabeticText(5);
			Response response = targetApi.createTask(taskName);

			Assertions.assertNotNull(response);
			Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
			Assertions.assertNotNull(response.getBody());

			@SuppressWarnings("rawtypes")
			Map responseMap = response.getBody().as(Map.class);
			Assertions.assertNotNull(responseMap.get("uid"));
			String taskUid = responseMap.get("uid").toString();
			taskUids.add(taskUid);
		}
		
	}

	@Order(2)
	@Test
	public void test02StartTimeTask() throws Exception {
		for (int i = 0; i < NUMBER_OF_TASKS; i++) {
			TimeTaskRequest request = new TimeTaskRequest();
			request.setTaskUid(taskUids.get(i));
			request.setInterval(1);
			request.setRepeats(5);

			Response response = targetApi.startTimeTask(request);

			Assertions.assertNotNull(response);
			Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
			Assertions.assertNotNull(response.getBody());
		}
	}

	@Order(3)
	@Test
	public void test03GetProgressByTaskUid() throws Exception {
		List<ServerSentEventSubscriber> subscribers = new ArrayList<>();
		for (int i = 0; i < NUMBER_OF_TASKS; i++) {
			ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<ServerSentEvent<String>>() {};
			String url = TargetUrlFactory.buildGetProgressUrl(port, taskUids.get(i));
			Flux<ServerSentEvent<String>> eventStream = WebClient.create().get().uri(url).retrieve().bodyToFlux(type);
			ServerSentEventSubscriber subscriber = new ServerSentEventSubscriber("TimeTaskTest" + i);
			eventStream.subscribe(subscriber);
			subscribers.add(subscriber);
		}
		TimeUnit.SECONDS.sleep(15);
		subscribers.forEach(s -> results.add(s.getResult()));
	}
	
	@Order(4)
	@Test
	public void test04ValidateTaskProgressResults() throws Exception {
		Assertions.assertNotNull(results);
		Assertions.assertEquals(NUMBER_OF_TASKS, results.size());

		for (int i = 0; i < NUMBER_OF_TASKS; i++) {
			ProgressResult result = results.get(i);
			Assertions.assertNotNull(result);
			Assertions.assertFalse(result.getIsError());
			Assertions.assertTrue(result.getIsCompleted());

			List<Map<String, String>> events = result.getEvents();
			Assertions.assertNotNull(events);
			Assertions.assertEquals(5, events.size());

			List<Integer> actualIds = events.stream().map(e->e.get("id")).map(s->Integer.parseInt(s)).collect(Collectors.toList());
			List<Integer> expectedIds = Lists.list(1, 2, 3, 4, 5);
			Assertions.assertEquals(expectedIds, actualIds);

			Set<String> eventValues = events.stream().map(e->e.get("event")).collect(Collectors.toSet());
			Assertions.assertEquals(1, eventValues.size());
			Assertions.assertEquals("progress", eventValues.iterator().next());
		}
	}

	@Order(5)
	@Test
	public void test05ValidateTaskProgressResultsMap() throws Exception {
		Map<Integer, Double> expectedValueById = TaskProgressDataFactory.buildMarixOfProgressByEventId();

		Assertions.assertNotNull(results);
		Assertions.assertEquals(NUMBER_OF_TASKS, results.size());

		for (int i = 0; i < NUMBER_OF_TASKS; i++) {
			ProgressResult result = results.get(i);
			Map<Integer, Double> actualValueById = TaskProgressDataFactory.buildMarixOfProgressByEventId(result);
			Assertions.assertEquals(expectedValueById, actualValueById);
		}
	}

}