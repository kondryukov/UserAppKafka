package org.example.mapper;

import org.example.domain.Notification;
import org.example.dto.CreateNotificationRequest;
import org.example.dto.NotificationResponse;
import org.example.events.UserEvent;
import org.springframework.stereotype.Component;

@Component
public final class NotificationMapper {

    public NotificationResponse toResponse(Notification notification) {
        return new NotificationResponse(
                notification.getEmail(),
                notification.getOperation()
        );
    }

    public Notification fromController(CreateNotificationRequest request) {
        return new Notification(request.getEmail(), request.getOperation());
    }

    public Notification fromProducer(UserEvent userEvent) {
        return new Notification(userEvent.getEmail(), userEvent.getOperation());
    }
}

