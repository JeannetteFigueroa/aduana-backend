package com.aduanas.msusuarios.exception;

public class RutDuplicadoException
        extends RuntimeException {

    public RutDuplicadoException(
            String mensaje) {

        super(mensaje);
    }
}
