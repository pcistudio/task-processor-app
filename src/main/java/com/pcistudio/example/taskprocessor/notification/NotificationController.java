package com.pcistudio.example.taskprocessor.notification;

import com.pcistudio.task.procesor.task.TaskParams;
import com.pcistudio.task.procesor.writer.TaskWriter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("/api/v1/notify")
public class NotificationController {

    private TaskWriter writer;

    public NotificationController(TaskWriter writer) {
        Assert.notNull(writer, "writer is required");
        this.writer = writer;
    }

    @PostMapping
    public ResponseEntity<Void> notify(@RequestBody @Valid @NotNull Notification notification) {
        Assert.notNull(notification, "notification is required");
        writer.writeTasks(
                TaskParams.builder()
                        .handlerName("email_notification")
                        .payload(notification)
                        .delay(Duration.ofMinutes(1))
                        .build()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
