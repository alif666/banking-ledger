package com.example.demo_app.dto;

import java.time.Instant;

public record ResponseDto(
    int status,
    String message,
    Instant timestamp
) {
}
