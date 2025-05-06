package com.example.mxh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GobelExcetion {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetais> ohterExceptionHandler(Exception e , WebRequest webRequest){
        ErrorDetais errorDetais = new ErrorDetais(e.getMessage(),webRequest.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetais>(errorDetais, HttpStatus.BAD_REQUEST) ;
    }
}
