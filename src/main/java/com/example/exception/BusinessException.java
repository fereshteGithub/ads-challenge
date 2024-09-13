package com.example.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    private int code = 0;
    public BusinessException() {
        super();
    }
    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    public BusinessException(String message, int code)
    {
        this(message);
        this.code = code;
    }
}
