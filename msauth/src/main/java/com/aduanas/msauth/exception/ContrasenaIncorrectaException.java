package com.aduanas.msauth.exception;

public class ContrasenaIncorrectaException extends RuntimeException {

    public ContrasenaIncorrectaException(String mensaje) {
        super(mensaje);
    }
}
