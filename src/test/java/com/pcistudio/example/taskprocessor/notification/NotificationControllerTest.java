package com.pcistudio.example.taskprocessor.notification;

import com.pcistudio.processor.test.handler.TaskInfoServiceTestHelper;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(OutputCaptureExtension.class)
class NotificationControllerTest {

    @LocalServerPort
    int port;

    RestClient restClient;

    TaskInfoServiceTestHelper taskInfoServiceTestHelper;

    @Autowired
    private RestClient.Builder restClientBuilder;

    @BeforeEach
    void setUp() {
        restClient = restClientBuilder.requestInterceptor(new LoggingInterceptor()).build();
    }

    @Test
    void testSms(CapturedOutput output) {

        SmsNotification smsNotification = new SmsNotification("John", "245924754", "Data");
        ResponseEntity<Void> response = restClient.post()

                .uri("http://localhost:%d/api/v1/notify/sms?delayMin={delayMin}".formatted(port), 0)
                .contentType(MediaType.APPLICATION_JSON)
                .body(smsNotification)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Awaitility.await()
                .untilAsserted(() -> {
                    assertThat(output).contains("SMS for John");
                });
    }
}