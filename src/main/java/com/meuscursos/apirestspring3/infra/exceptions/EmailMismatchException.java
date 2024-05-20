package com.meuscursos.apirestspring3.infra.exceptions;

public class EmailMismatchException extends RuntimeException {
    public EmailMismatchException(String message) {
        super(message);
    }
}
