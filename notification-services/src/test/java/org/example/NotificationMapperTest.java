package org.example;

import org.example.domain.Notification;
import org.example.dto.CreateNotificationRequest;
import org.example.dto.NotificationResponse;
import org.example.events.OperationType;
import org.example.events.UserEvent;
import org.example.mapper.NotificationMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class NotificationMapperTest {

    @InjectMocks
    private NotificationMapper notificationMapper;

    @Test
    void fromController() {
        CreateNotificationRequest request = new CreateNotificationRequest("name@mail.ru", OperationType.CREATE);

        Notification notification = notificationMapper.fromController(request);
        assertThat(notification.getEmail()).isEqualTo("name@mail.ru");
        assertThat(notification.getOperation()).isEqualTo(OperationType.CREATE);
    }

    @Test
    void fromProducer() {
        UserEvent userEvent = new UserEvent();
        userEvent.setEmail("name@mail.ru");
        userEvent.setOperation(OperationType.DELETE);

        Notification notification = notificationMapper.fromProducer(userEvent);
        assertThat(notification.getEmail()).isEqualTo("name@mail.ru");
        assertThat(notification.getOperation()).isEqualTo(OperationType.DELETE);

    }

    @Test
    void toResponse() {
        Notification notification = new Notification("name@mail.ru", OperationType.DELETE);
        NotificationResponse notificationResponse = notificationMapper.toResponse(notification);

        assertThat(notificationResponse).isEqualTo(new NotificationResponse("name@mail.ru", OperationType.DELETE));
    }
}