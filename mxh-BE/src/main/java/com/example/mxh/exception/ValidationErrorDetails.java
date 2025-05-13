package com.example.mxh.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ValidationErrorDetails {
    private String message;
    private String details;
    private LocalDateTime timestamp;
    private Map<String, String> validationErrors;
}