package com.main.Test9.Exception;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message){
        super(message);
    }
    public ResourceConflictException(String message,Throwable cause){
        super(message,cause);
    }
}
