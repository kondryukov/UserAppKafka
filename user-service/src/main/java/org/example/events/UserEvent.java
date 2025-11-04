package org.example.events;

import org.springframework.stereotype.Component;

@Component
public class UserEvent {
    private String email;
    private String operation; // "CREATED"/"DELETED"


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
