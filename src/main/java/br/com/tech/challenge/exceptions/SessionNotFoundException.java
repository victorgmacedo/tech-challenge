package br.com.tech.challenge.exceptions;

public class SessionNotFoundException extends NotFoundException {
    public SessionNotFoundException(String message) {
        super(message);
    }
}
