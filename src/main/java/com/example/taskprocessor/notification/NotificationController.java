package com.example.taskprocessor.notification;

import com.pcistudio.task.procesor.task.TaskParams;
import com.pcistudio.task.procesor.writer.TaskWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
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
    public void notify(Notification notification) {
        writer.writeTasks(
                TaskParams.builder()
                        .handlerName("email_notification")
                        .payload(notification)
                        .delay(Duration.ofDays(1))
                        .build()
        );

    }
}
