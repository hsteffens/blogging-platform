package com.blogging.platform.exceptions;

public class RecordUpdateException extends RuntimeException {

    String message;

    public RecordUpdateException(String message) {
        super(message);
    }

}
