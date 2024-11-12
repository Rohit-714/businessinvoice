package com.managment.businessinvoice.exception;

public class AdminNotFoundException extends  RuntimeException {
    public AdminNotFoundException() {
    }

    public AdminNotFoundException(String message) {
        super(message);
    }
}