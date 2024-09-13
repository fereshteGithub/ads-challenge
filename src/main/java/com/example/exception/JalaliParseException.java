package com.example.exception;

public class JalaliParseException extends RuntimeException {

    private String date;

    public JalaliParseException(String date) {
        super("The jalali date you entered is not valid: " + date);
        this.date = date;
    }

    public JalaliParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getDate() {
        return date;
    }
}
