package com.notes.validations;

public class ValidationModelError extends RuntimeException{
    public ValidationModelError(String message) {
        super(message);
    }
    
}
