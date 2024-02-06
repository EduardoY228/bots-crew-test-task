package org.botscrew.testtask.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message){
        super(message);
    }

    public static ValidationException throwIt(String message) {
        throw new ValidationException(message);
    }
}
