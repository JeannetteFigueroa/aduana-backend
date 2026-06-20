package com.aduanas.msusuarios.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(
            RuntimeException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("timestamp",
                LocalDateTime.now());

        response.put("status",
                HttpStatus.BAD_REQUEST.value());

        response.put("message",
                ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationException(
            MethodArgumentNotValidException ex) {

        Map<String, Object> response = new HashMap<>();

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error
                        -> errors.put(
                        error.getField(),
                        error.getDefaultMessage()));

        response.put("timestamp",
                LocalDateTime.now());

        response.put("status",
                HttpStatus.BAD_REQUEST.value());

        response.put("errors",
                errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(
            Exception ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("timestamp",
                LocalDateTime.now());

        response.put("status",
                HttpStatus.INTERNAL_SERVER_ERROR.value());

        response.put("message",
                ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
