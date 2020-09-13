package com.aa.combinator.exception;

/**
 * Created by mural on 12/09/2020.
 */
public class DBException extends RuntimeException {

    public DBException(String message) {
        super(message);
    }

    public DBException(Throwable cause) {
        super(cause);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
