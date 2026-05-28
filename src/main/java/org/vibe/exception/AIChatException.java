package org.vibe.exception;

public class AIChatException extends RuntimeException {
    public AIChatException(String message) {
        super(message);
    }

    public AIChatException(String message, Throwable cause) {
        super(message, cause);
    }
}
