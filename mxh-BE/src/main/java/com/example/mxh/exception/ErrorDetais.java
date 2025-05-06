package com.example.mxh.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetais {
    private String message;
    private String error;
    private LocalDateTime timeStamp;
}
