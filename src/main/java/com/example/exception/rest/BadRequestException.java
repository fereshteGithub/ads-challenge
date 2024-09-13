package com.example.exception.rest;


import com.example.exception.BusinessException;

public class BadRequestException extends BusinessException {
    public BadRequestException(String message){
        super(message);
    }

    public BadRequestException(String message,int code){
        super(message,code);
    }
}
