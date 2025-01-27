package br.com.mouts.order.application.exception;

import java.time.Instant;

public class GenericError {

    private String message;
    private int code;
    private Instant timestamp;

    public GenericError(String message, int code) {
        this.message = message;
        this.code = code;
        this.timestamp = Instant.now();
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
