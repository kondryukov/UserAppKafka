package org.example.messaging;

import org.example.events.UserEvent;
import org.example.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationKafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(NotificationKafkaConsumer.class);
    private final NotificationService notificationService;

    public NotificationKafkaConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    //    @KafkaListener(topics = "users")
    @KafkaListener(
            topics = "${app.kafka.user-topic:users}",
            groupId = "notification-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(UserEvent userEvent) {
        log.info("Consuming user {}", userEvent.getEmail());
        notificationService.sendEmail(userEvent);
    }
}
