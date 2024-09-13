package com.example.exception.rest;


import com.example.exception.BusinessException;

public class NotFoundException extends BusinessException {

    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(String message,int code){
        super(message,code);
    }
}
