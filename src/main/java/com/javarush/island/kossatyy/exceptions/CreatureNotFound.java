package com.javarush.island.kossatyy.exceptions;

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
