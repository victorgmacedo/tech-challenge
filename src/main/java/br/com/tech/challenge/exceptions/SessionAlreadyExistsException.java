package br.com.tech.challenge.exceptions;

public class SessionAlreadyExistsException extends ApplicationException {
    public SessionAlreadyExistsException(String message) {
        super(message);
    }
}
