package de.hhu.stups.plues.data;

public class StoreExeception extends Throwable {
    private final Exception exception;

    public StoreExeception(final Exception e) {
        this.exception = e;
    }

    public Exception getException() {
        return exception;
    }
}