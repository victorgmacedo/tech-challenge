package br.com.tech.challenge.exceptions;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String message) {
        super(message);
    }
}
