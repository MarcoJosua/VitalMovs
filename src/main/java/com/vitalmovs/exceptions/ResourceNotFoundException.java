package com.vitalmovs.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(){ super(); }
    public ResourceNotFoundException(String message) { super(message); }

}
