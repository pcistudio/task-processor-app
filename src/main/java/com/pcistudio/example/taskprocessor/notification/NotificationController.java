package com.pcistudio.example.taskprocessor.notification;

import com.pcistudio.task.procesor.task.TaskMetadata;
import com.pcistudio.task.procesor.task.TaskParams;
import com.pcistudio.task.procesor.writer.TaskWriter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("/api/v1/notify")
public class NotificationController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    private final TaskWriter writer;

    public NotificationController(TaskWriter writer) {
        Assert.notNull(writer, "writer is required");
        this.writer = writer;
    }

    @PostMapping("/email")
    public ResponseEntity<Void> notifyViaEmail(@RequestBody @Valid @NotNull EmailNotification notification) {
        Assert.notNull(notification, "notification is required");

        writer.writeTasks(
                TaskParams.builder()
                        .handlerName("email")
                        .payload(notification)
                        .delay(Duration.ofMinutes(1))
                        .build()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/sms")
    public ResponseEntity<Void> notifyViaSms(@RequestParam(required=false, defaultValue = "1") Integer delayMin,  @RequestBody @Valid @NotNull SmsNotification notification) {
        Assert.notNull(notification, "notification is required");

        TaskMetadata sms = writer.writeTasks(
                TaskParams.builder()
                        .handlerName("sms")
                        .payload(notification)
                        .delay(Duration.ofMinutes(delayMin))
                        .build()
        );
        logger.info("sms notification save taskId={} with delayMin={} min", sms.getId(), delayMin);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
