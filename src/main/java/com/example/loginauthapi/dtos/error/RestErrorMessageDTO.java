package com.example.loginauthapi.dtos.error;

import org.springframework.http.HttpStatus;

public record RestErrorMessageDTO(HttpStatus status, Boolean error, String message) {
}
