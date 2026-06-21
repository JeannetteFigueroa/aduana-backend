package com.aduanas.msauth.exception;

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

        @ExceptionHandler(EmailDuplicadoException.class)
        public ResponseEntity<ApiError> emailDuplicado(
                        EmailDuplicadoException ex,
                        HttpServletRequest request) {

                ApiError error = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CONFLICT.value())
                                .error("Conflict")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(error);
        }

        @ExceptionHandler(RutDuplicadoException.class)
        public ResponseEntity<ApiError> rutDuplicado(
                        RutDuplicadoException ex,
                        HttpServletRequest request) {

                ApiError error = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CONFLICT.value())
                                .error("Conflict")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(error);
        }

        @ExceptionHandler(UsuarioNoEncontradoException.class)
        public ResponseEntity<ApiError> usuarioNoEncontrado(
                        UsuarioNoEncontradoException ex,
                        HttpServletRequest request) {

                ApiError error = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.NOT_FOUND.value())
                                .error("Not Found")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(error);
        }

        @ExceptionHandler(CredencialesInvalidasException.class)
        public ResponseEntity<ApiError> credencialesInvalidas(
                        CredencialesInvalidasException ex,
                        HttpServletRequest request) {

                ApiError error = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .error("Unauthorized")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(error);
        }

        @ExceptionHandler(ContrasenaIncorrectaException.class)
        public ResponseEntity<ApiError> contrasenaIncorrecta(
                        ContrasenaIncorrectaException ex,
                        HttpServletRequest request) {

                ApiError error = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error("Bad Request")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .badRequest()
                                .body(error);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, Object>> validacion(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                Map<String, String> errors = new HashMap<>();
                ex.getBindingResult()
                                .getFieldErrors()
                                .forEach(error -> errors.put(
                                                error.getField(),
                                                error.getDefaultMessage()));

                Map<String, Object> response = new HashMap<>();
                response.put("timestamp", LocalDateTime.now());
                response.put("status", HttpStatus.BAD_REQUEST.value());
                response.put("error", "Bad Request");
                response.put("message", "Error de validación");
                response.put("errors", errors);
                response.put("path", request.getRequestURI());

                return ResponseEntity
                                .badRequest()
                                .body(response);
        }

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ApiError> runtimeException(
                        RuntimeException ex,
                        HttpServletRequest request) {

                ApiError error = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error("Bad Request")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .badRequest()
                                .body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiError> excepcionGeneral(
                        Exception ex,
                        HttpServletRequest request) {

                ApiError error = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .error("Internal Server Error")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(error);
        }
}
