package com.acheron.megalaba.security.exception.custom;

public class PhoneNumberAlreadyExists extends RuntimeException{
    public PhoneNumberAlreadyExists() {
        super();
    }

    public PhoneNumberAlreadyExists(String message) {
        super(message);
    }
}
