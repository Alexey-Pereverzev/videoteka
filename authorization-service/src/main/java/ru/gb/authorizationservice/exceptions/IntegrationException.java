package ru.gb.authorizationservice.exceptions;

public class IntegrationException extends RuntimeException {
    public IntegrationException(String message) {
        super(message);
    }
}
