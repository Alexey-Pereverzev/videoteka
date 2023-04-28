package ru.gb.cartservice.exceptions;

public class ProductServiceIntegrationException extends RuntimeException {
    public ProductServiceIntegrationException(String message) {
        super(message);
    }
}
