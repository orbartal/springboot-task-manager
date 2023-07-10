package demo.springboot.task.manager.test;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import demo.springboot.task.manager.utils.TargetUrlFactory;
import io.restassured.response.Response;
import reactor.core.publisher.Flux;


@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MultiListnersForOneTimeTaskTest {

	private static final int NUMBER_OF_LISTNERS = 10;
	private static Optional<String> taskUid = Optional.empty();
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
		Response response = targetApi.createTask();

		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		Assertions.assertNotNull(response.getBody());

		@SuppressWarnings("rawtypes")
		Map responseMap = response.getBody().as(Map.class);
		Assertions.assertNotNull(responseMap.get("uid"));
		taskUid = Optional.of(responseMap.get("uid").toString());
	}

	@Order(2)
	@Test
	public void test02StartTimeTask() throws Exception {
		TimeTaskRequest request = new TimeTaskRequest();
		request.setTaskUid(taskUid.get());
		request.setInterval(1);
		request.setRepeats(5);

		Response response = targetApi.startTimeTask(request);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
		Assertions.assertNotNull(response.getBody());
	}

	@Order(3)
	@Test
	public void test03GetProgressByTaskUid() throws Exception {
		List<ServerSentEventSubscriber> subscribers = new ArrayList<>();
		for (int i = 0; i < NUMBER_OF_LISTNERS; i++) {
			ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<ServerSentEvent<String>>() {};
			String url = TargetUrlFactory.buildGetProgressUrl(port, taskUid.get());
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
		Assertions.assertEquals(NUMBER_OF_LISTNERS, results.size());

		for (int i = 0; i < NUMBER_OF_LISTNERS; i++) {
			System.out.println(i + ") test04ValidateTaskProgressResults");
			ProgressResult result = results.get(i);
			Assertions.assertNotNull(result);
			Assertions.assertFalse(result.getIsError());
			Assertions.assertTrue(result.getIsCompleted());

			List<Map<String, String>> events = result.getEvents();
			Assertions.assertNotNull(events);
			System.out.println("events: " + events);
			Assertions.assertEquals(5, events.size());

			List<Integer> actualIds = events.stream().map(e->e.get("id")).map(s->Integer.parseInt(s)).collect(Collectors.toList());
			List<Integer> expectedIds = Lists.list(1, 2, 3, 4, 5);
			Assertions.assertEquals(expectedIds, actualIds);

			Set<String> eventValues = events.stream().map(e->e.get("event")).collect(Collectors.toSet());
			Assertions.assertEquals(1, eventValues.size());
			Assertions.assertEquals(taskUid.get(), eventValues.iterator().next());
		}
	}

	@Order(5)
	@Test
	public void test05ValidateTaskProgressResultsMap() throws Exception {
		@SuppressWarnings("serial")
		Map<Integer, Double> expectedValueById = new HashMap<Integer, Double>() {
			{
				put(1, 0.0);
				put(2, 0.2);
				put(3, 0.4);
				put(4, 0.6);
				put(5, 0.8);
			}
		};

		Assertions.assertNotNull(results);
		Assertions.assertEquals(NUMBER_OF_LISTNERS, results.size());

		for (int i = 0; i < NUMBER_OF_LISTNERS; i++) {
			ProgressResult result = results.get(i);
			List<Map<String, String>> events = result.getEvents();
			Map<Integer, Double> actualValueById = events.stream().collect(
					Collectors.toMap(e -> Integer.parseInt(e.get("id")), e -> Double.parseDouble(e.get("data"))));
			Assertions.assertEquals(expectedValueById, actualValueById);
		}
	}

}