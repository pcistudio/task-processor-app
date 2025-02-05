#  Sample application for `task-processor-app`

This is a Spring Boot application that demonstrates how to use the [`task-processor`](https://github.com/pcistudio/task-processor.git) library.


## Getting Started

```bash
mvn clean install;
mvn spring-boot:run;
```
The application will start on port `8082` and you can access the following endpoints:

- `POST http://localhost:8082/api/v1/notify`

```json
{
  "personName": "Person Name",
  "email": "email@gmail.com",
  "message": "Appointment Tomorrow 2"
}
```
You can use the postman collection in the `postman` folder to test the endpoint.
or you can use the following curl command:

```bash
curl -X POST "http://localhost:8082/api/v1/notify" -H "Content-Type: application/json" -d "{\"personName\":\"Person Name\",\"email\":\"email@gmail.com\",\"message\":\"Appointment Tomorrow 2\"}"

```

## H2 Console

You can also access the H2 console at `http://localhost:8082/h2-console` with the following test credentials:
```properties
JDBC URL:jdbc:h2:mem:testdb
User Name: sa
password: <blank>
```
Here you can see the notification tables and the data that is being saved.
* `task_info_email_notification` table. This table will store the notification metadata, retry information and payload.
* `task_info_email_notification_error` table. This table will store the any error that happens during the delivery of the notification.


### Reference Documentation
For further reference, please consider the following official documentation:

* [Task Processor Reference Guide](https://pcistudio.github.io/task-processor/)
