# springboot-task-manager
An example app for curd and other features of manage long async tasks

### How to buid, test & run the server
1. Build the project: mvn clean install -DskipTests
2. Test the servr: mvn test
3. Run the servr: mvn spring-boot:run
4. Open browser in url: [springboot-swagger-ui](http://localhost:8075/swagger-ui/index.html#)
5. Also open the frontend in ...\springboot-task-manager\frontend\event_listener_with_progress_bar.html

Note: 
- The event_listener is a very simple flient, a single page with both HTML and JS.
- However, it can listen to events from the springserver. A feature that does not exists in Swagger.

### How to use the task progress bar
1. Run the server & open cients as explain in previous section.
2. In the swagger ui create a task.
    1. POST /api/v1/task/create.
    2. Insert a task name. For example: task1
    3. You shoud get a taskUid in the response. Save this ID.
3. In the swagger ui start running the task.
   1. POST /api/v1/time/task
   2. Use the same taskUid as before.
   3. Insert values for interval & repeats. For example 10 for both.
4. Open the event_listener ui.
   1. Use the default localhost server and port.
   2. Insert the taskId.
   3. Click on the button 'Click Me'
5. Now see the events printed in a list in the event_listener ui  and the progress bar update accordingly.