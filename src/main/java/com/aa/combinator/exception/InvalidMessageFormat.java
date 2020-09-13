package com.aa.combinator.exception;

/**
 * Created by mural on 12/09/2020.
 */
public class InvalidMessageFormat extends RuntimeException {

    public InvalidMessageFormat(String message) {
        super(message);
    }

    public InvalidMessageFormat(Throwable cause) {
        super(cause);
    }

    public InvalidMessageFormat(String message, Throwable cause) {
        super(message, cause);
    }
}
