package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.events.OperationType;

public class CreateNotificationRequest {
    @NotBlank
    @Email
    @Size(max = 254)
    private String email;

    @NotNull(message = "operation is required and must be 'CREATE' or 'DELETE'")
    private OperationType operation;

    public CreateNotificationRequest(String email, OperationType operation) {
        this.email = email;
        this.operation = operation;
    }

    public String getEmail() {
        return email;
    }

    public OperationType getOperation() {
        return operation;
    }
}