package org.example.service;

import org.example.domain.Notification;
import org.example.dto.CreateNotificationRequest;
import org.example.dto.NotificationResponse;
import org.example.events.UserEvent;
import org.example.mapper.NotificationMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationMapper notificationMapper;
    public final JavaMailSender emailSender;
    private final String createReplica = "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.";
    private final String deletedReplica = "Здравствуйте! Ваш аккаунт был удалён.";

    public NotificationService(JavaMailSender emailSender, NotificationMapper notificationMapper) {
        this.emailSender = emailSender;
        this.notificationMapper = notificationMapper;
    }

    public NotificationResponse sendEmail(CreateNotificationRequest request) {
        Notification notification = notificationMapper.fromController(request);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(notification.getEmail());
        simpleMailMessage.setSubject(notification.getOperation());
        simpleMailMessage.setText(notification.getOperation().equals("create") ? createReplica : deletedReplica);
        emailSender.send(simpleMailMessage);

        return notificationMapper.toResponse(notification);
    }

    public void sendEmail(UserEvent userEvent) {
        Notification notification = notificationMapper.fromProducer(userEvent);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(notification.getEmail());
        simpleMailMessage.setSubject(notification.getOperation());
        simpleMailMessage.setText(notification.getOperation().equals("create") ? createReplica : deletedReplica);
        emailSender.send(simpleMailMessage);
    }
}
