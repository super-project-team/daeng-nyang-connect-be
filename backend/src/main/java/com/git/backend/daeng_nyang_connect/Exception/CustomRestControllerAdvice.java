package com.git.backend.daeng_nyang_connect.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomRestControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleCustomException(NoSuchElementException ne) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ne.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleCustomException(IllegalArgumentException ie) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ie.getMessage());
    }
}
