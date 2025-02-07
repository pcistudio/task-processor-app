package com.pcistudio.example.taskprocessor.notification;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailNotification(@NotBlank String personName,@NotBlank @Email String email, @NotBlank String message) {
}