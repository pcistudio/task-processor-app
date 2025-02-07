package com.pcistudio.example.taskprocessor.notification;

import jakarta.validation.constraints.NotBlank;

public record SmsNotification(@NotBlank String personName, @NotBlank String phone, @NotBlank String message) {
}