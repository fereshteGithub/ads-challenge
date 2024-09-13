package com.example.exception;

public class ResourceNotFoundException extends BusinessException{
    public ResourceNotFoundException()
    {
        super("not.found");
    }
    public ResourceNotFoundException(String message)
    {
        super(message);
    }
}
