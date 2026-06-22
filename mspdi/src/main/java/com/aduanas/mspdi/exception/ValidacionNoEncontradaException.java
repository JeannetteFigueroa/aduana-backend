package com.aduanas.mspdi.exception;

public class ValidacionNoEncontradaException extends RuntimeException {
    public ValidacionNoEncontradaException(Long id) {
        super("Validación no encontrada: " + id);
    }
}