package com.pcistudio.example.taskprocessor.notification;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Notification(@NotBlank String personName, @Email String email, @NotBlank String message) {
}