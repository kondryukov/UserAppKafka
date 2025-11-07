package org.example.dto;

import org.example.events.OperationType;

public record NotificationResponse(
        String email,
        OperationType operation
) {
}

