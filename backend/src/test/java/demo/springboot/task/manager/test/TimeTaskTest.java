package demo.springboot.task.manager.test;

import java.net.HttpURLConnection;
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
import demo.springboot.task.manager.utils.TaskProgressDataFactory;
import io.restassured.response.Response;
import reactor.core.publisher.Flux;


@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimeTaskTest {

	private static Optional<String> taskUid = Optional.empty();
	private static Optional<ProgressResult> pResult  = Optional.empty();

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
		ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<ServerSentEvent<String>>() {};
		String url = TargetUrlFactory.buildGetProgressUrl(port, taskUid.get());
		Flux<ServerSentEvent<String>> eventStream = WebClient.create().get().uri(url).retrieve().bodyToFlux(type);
		ServerSentEventSubscriber subscriber = new ServerSentEventSubscriber("TimeTaskTest");
		eventStream.subscribe(subscriber);
		TimeUnit.SECONDS.sleep(10);
		pResult = Optional.ofNullable(subscriber.getResult());
	}
	
	@Order(4)
	@Test
	public void test04ValidateTaskProgressResults() throws Exception {
		Assertions.assertTrue(pResult.isPresent());
		ProgressResult result = pResult.get();
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
		Assertions.assertEquals(taskUid.get(), eventValues.iterator().next());
	}

	@Order(5)
	@Test
	public void test05ValidateTaskProgressResultsMap() throws Exception {
		Map<Integer, Double> actualValueById = pResult.get().getEvents().stream()
				.collect(Collectors.toMap(e -> Integer.parseInt(e.get("id")), e -> Double.parseDouble(e.get("data"))));

		Map<Integer, Double> expectedValueById = TaskProgressDataFactory.buildMarixOfProgressByEventId();
		Assertions.assertEquals(expectedValueById, actualValueById);
	}

}