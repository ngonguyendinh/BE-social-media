package com.example.mxh.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GobelExcetion extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {


        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ValidationErrorDetails validationErrorDetails = new ValidationErrorDetails(
                "Validation Failed",
                request.getDescription(false),
                LocalDateTime.now(),
                errors
        );

return  new ResponseEntity<>(validationErrorDetails, HttpStatus.BAD_REQUEST);
//        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

        @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetais> otherExceptionHandler(Exception e, WebRequest webRequest) {
        ErrorDetais errorDetais = new ErrorDetais(
                e.getMessage(),
                webRequest.getDescription(false),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorDetais, HttpStatus.BAD_REQUEST);
    }
}
