package org.example.domain;

public class Notification {
    private String email;
    private String operation;

    public Notification(String email, String operation) {
        this.email = email;
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
