package com.vitalmovs.exceptions;

public class IncompleteDataException extends RuntimeException {

    public IncompleteDataException() { super(); }
    public IncompleteDataException(String message) { super(message); }

}
