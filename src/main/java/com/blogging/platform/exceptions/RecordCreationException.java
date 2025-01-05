package com.blogging.platform.exceptions;

public class RecordCreationException extends RuntimeException {

    String message;

    public RecordCreationException(String message) {
        super(message);
    }

}
