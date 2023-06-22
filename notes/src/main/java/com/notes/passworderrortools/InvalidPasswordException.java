package com.notes.passworderrortools;

public class InvalidPasswordException extends RuntimeException {
    
        public InvalidPasswordException(String message) {
            super(message);
        }

}
