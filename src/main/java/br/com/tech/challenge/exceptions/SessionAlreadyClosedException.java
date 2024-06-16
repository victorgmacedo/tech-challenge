package br.com.tech.challenge.exceptions;

public class SessionAlreadyClosedException extends ApplicationException {
    public SessionAlreadyClosedException(String message) {
        super(message);
    }
}

