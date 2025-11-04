package org.example.controller;

import org.example.service.NotificationService;
import org.example.dto.CreateNotificationRequest;
import org.example.dto.NotificationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/notification", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController {

    private final NotificationService notificationService;
    private final Logger log = LoggerFactory.getLogger(NotificationController.class);

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationResponse> createUserNotification(@RequestBody CreateNotificationRequest request) {
        NotificationResponse created = notificationService.sendEmail(request);
        log.info("Notification is created: {}", created);
        return ResponseEntity.ok(created);
    }
}
