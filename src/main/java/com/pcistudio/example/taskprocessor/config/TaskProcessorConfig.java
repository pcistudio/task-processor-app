package com.pcistudio.example.taskprocessor.config;

import com.pcistudio.example.taskprocessor.notification.Notification;
import com.pcistudio.task.procesor.HandlerProperties;
import com.pcistudio.task.procesor.handler.TaskHandler;
import com.pcistudio.task.procesor.register.HandlerManagerImpl;
import com.pcistudio.task.processor.config.AbstractHandlersConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.TransientDataAccessException;

import java.time.Duration;
import java.util.Set;

@Slf4j
@Configuration
public class TaskProcessorConfig extends AbstractHandlersConfiguration {
    public static final String HANDLER = "email_notification";
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";

    @Override
    protected void configureHandler(HandlerManagerImpl.Builder builder) {
        builder.register(
                HandlerProperties.builder()
                        .handlerName(HANDLER)
                        .tableName(HANDLER)
                        .requeueInterval(120000)
                        .processingExpire(Duration.ofMinutes(2))
                        .transientExceptions(Set.of(TransientDataAccessException.class))
                        .taskHandler(new NotifyHandler())
                        .build()
        );
    }

    static class NotifyHandler implements TaskHandler<Notification> {
        @Override
        public void process(Notification notification) {
            log.info(GREEN + "Notification for {}<{}> message: {}" + RESET, notification.personName(), notification.email(), notification.message());
        }
    }


}
