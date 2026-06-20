package com.aduanas.msusuarios.exception;

public class FuncionarioNoEncontradoException
        extends RuntimeException {

    public FuncionarioNoEncontradoException(
            String mensaje) {

        super(mensaje);
    }
}