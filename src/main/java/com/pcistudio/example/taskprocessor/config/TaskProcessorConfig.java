package com.pcistudio.example.taskprocessor.config;

import com.pcistudio.example.taskprocessor.notification.EmailNotification;
import com.pcistudio.example.taskprocessor.notification.SmsNotification;
import com.pcistudio.task.processor.config.AbstractHandlersConfiguration;
import com.pcistudio.task.procesor.HandlerProperties;
import com.pcistudio.task.procesor.handler.TaskHandler;
import com.pcistudio.task.procesor.register.HandlerManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.TransientDataAccessException;

import java.time.Duration;
import java.util.Set;

@Slf4j
@Configuration
public class TaskProcessorConfig extends AbstractHandlersConfiguration {
    public static final String TABLE = "notifications";
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";

    @Override
    protected void configureHandler(HandlerManagerImpl.Builder builder) {
        builder.register(
                HandlerProperties.builder()
                        .handlerName("email")
                        .tableName(TABLE)
                        .requeueInterval(120000)
                        .processingExpire(Duration.ofMinutes(2))
                        .transientExceptions(Set.of(TransientDataAccessException.class))
                        .taskHandler(new EmailNotifyHandler())
                        .build()
        ).register(
                HandlerProperties.builder()
                        .handlerName("sms")
                        .tableName(TABLE)
                        .requeueInterval(120000)
                        .processingExpire(Duration.ofMinutes(2))
                        .transientExceptions(Set.of(TransientDataAccessException.class))
                        .taskHandler(new SmsNotifyHandler())
                        .build()
        );
    }

    static class EmailNotifyHandler implements TaskHandler<EmailNotification> {
        @Override
        public void process(EmailNotification notification) {
            log.info(GREEN + "Email for {}<{}> message: {}" + RESET, notification.personName(), notification.email(), notification.message());
        }
    }

    static class SmsNotifyHandler implements TaskHandler<SmsNotification> {
        @Override
        public void process(SmsNotification notification) {
            log.info(GREEN + "SMS for {}<{}> message: {}" + RESET, notification.personName(), notification.phone(), notification.message());
        }
    }


}
