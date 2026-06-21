package com.aduanas.msviajeros.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(RutDuplicadoException.class)
        public ResponseEntity<Map<String, Object>> rutDuplicado(
                        RutDuplicadoException ex,
                        HttpServletRequest request) {

                Map<String, Object> response = new HashMap<>();
                response.put("timestamp", LocalDateTime.now());
                response.put("status", HttpStatus.CONFLICT.value());
                response.put("error", "Conflict");
                response.put("message", ex.getMessage());
                response.put("path", request.getRequestURI());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        @ExceptionHandler(ViajeroNoEncontradoException.class)
        public ResponseEntity<Map<String, Object>> noEncontrado(
                        ViajeroNoEncontradoException ex,
                        HttpServletRequest request) {

                Map<String, Object> response = new HashMap<>();
                response.put("timestamp", LocalDateTime.now());
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("error", "Not Found");
                response.put("message", ex.getMessage());
                response.put("path", request.getRequestURI());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, Object>> validacion(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                Map<String, String> errors = new HashMap<>();
                ex.getBindingResult()
                                .getFieldErrors()
                                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));

                Map<String, Object> response = new HashMap<>();
                response.put("timestamp", LocalDateTime.now());
                response.put("status", HttpStatus.BAD_REQUEST.value());
                response.put("error", "Bad Request");
                response.put("message", "Error de validación");
                response.put("errors", errors);
                response.put("path", request.getRequestURI());
                return ResponseEntity.badRequest().body(response);
        }

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<Map<String, Object>> runtime(RuntimeException ex,
                        HttpServletRequest request) {

                Map<String, Object> response = new HashMap<>();
                response.put("timestamp", LocalDateTime.now());
                response.put("status", HttpStatus.BAD_REQUEST.value());
                response.put("error", "Bad Request");
                response.put("message", ex.getMessage());
                response.put("path", request.getRequestURI());
                return ResponseEntity.badRequest().body(response);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Map<String, Object>> general(Exception ex,
                        HttpServletRequest request) {

                Map<String, Object> response = new HashMap<>();
                response.put("timestamp", LocalDateTime.now());
                response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.put("error", "Internal Server Error");
                response.put("message", ex.getMessage());
                response.put("path", request.getRequestURI());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
}
