package com.aduanas.msaduana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
public class MsaduanaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsaduanaApplication.class, args);
    }
}

@RestControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
    ) {}

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handle(RuntimeException ex) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(
                LocalDateTime.now(),
                400,
                "Bad Request",
                ex.getMessage(),
                "/api/vehiculos"
            ));
    }
}