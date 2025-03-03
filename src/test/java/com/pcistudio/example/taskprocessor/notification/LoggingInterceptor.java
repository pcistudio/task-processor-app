package com.pcistudio.example.taskprocessor.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        logger.info("➡️ Request: {} {}", request.getMethod(), request.getURI());
        logger.info("Headers: {}", request.getHeaders());
        if (body.length > 0) {
            logger.info("Body: {}", new String(body, StandardCharsets.UTF_8));
        }
    }

    private void logResponse(ClientHttpResponse response) {
        try {
            String responseBody = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));
            logger.info("⬅️ Response: {} {}", response.getStatusCode(), response.getStatusText());
            logger.info("Headers: {}", response.getHeaders());
            logger.info("Body: {}", responseBody);
        } catch (Exception e) {
            logger.error("Error reading response body", e);
        }
    }
}