package com.acheron.megalaba.security.exception.custom;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
    public UserAlreadyExistsException() {
        super();
    }
}
