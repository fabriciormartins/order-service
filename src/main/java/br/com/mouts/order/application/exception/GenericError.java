package br.com.mouts.order.application.exception;

import java.time.Instant;

public class GenericError {

    private String message;
    private int code;
    private Long timestamp;

    public GenericError(String message, int code) {
        this.message = message;
        this.code = code;
        this.timestamp = Instant.now().toEpochMilli();
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
