package com.example.exception;

public class LookupNotFoundException extends RuntimeException {
    private long lookupId;

    public LookupNotFoundException(long lookupId) {
        this("Lookup not found with ID: [%d]".formatted(lookupId));
        this.lookupId = lookupId;
    }

    public LookupNotFoundException(String message) {
        super(message);
    }

    public LookupNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public long getLookupId() {
        return lookupId;
    }
}
