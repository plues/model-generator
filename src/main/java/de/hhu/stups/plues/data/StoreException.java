package de.hhu.stups.plues.data;

public class StoreException extends Exception {
  private final Exception exception;

  public StoreException(final Exception exception) {
    this.exception = exception;
  }

  public Exception getException() {
    return exception;
  }
}
