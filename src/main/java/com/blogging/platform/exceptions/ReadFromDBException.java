package com.blogging.platform.exceptions;

public class ReadFromDBException extends RuntimeException {

    String message;

    public ReadFromDBException(String message) {
        super(message);
    }

}
