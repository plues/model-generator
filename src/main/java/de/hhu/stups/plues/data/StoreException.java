package de.hhu.stups.plues.data;

public class StoreException extends Exception {
    private final Exception exception;

    public StoreException(final Exception e) {
        this.exception = e;
    }

    public Exception getException() {
        return exception;
    }
}
