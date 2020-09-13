package com.aa.combinator.exception;

/**
 * Created by mural on 12/09/2020.
 */
public class ErrorMessage {

    public static final String INVALID_FORMAT = "Wrong message format, the json input should be of the form { \n" +
            "   \"input\":[ \n" + " \"A\",\n" + "\"B\"\n" + "]\n" + "}\n";
    public static final String PERSIST_ERROR = "Error persisting information to database, please retry or contact support";
}
