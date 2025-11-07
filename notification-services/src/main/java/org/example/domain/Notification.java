package org.example.domain;

import org.example.events.OperationType;

public class Notification {
    private String email;
    private OperationType operation;

    public Notification(String email, OperationType operation) {
        this.email = email;
        this.operation = operation;
    }

    public OperationType getOperation() {
        return operation;
    }

    public String getEmail() {
        return email;
    }
}
