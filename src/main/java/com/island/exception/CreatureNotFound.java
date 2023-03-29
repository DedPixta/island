package com.island.exception;

@SuppressWarnings("unused")
public class CreatureNotFound extends RuntimeException {

    public CreatureNotFound() {
    }

    public CreatureNotFound(String message) {
        super(message);
    }

    public CreatureNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public CreatureNotFound(Throwable cause) {
        super(cause);
    }

}
