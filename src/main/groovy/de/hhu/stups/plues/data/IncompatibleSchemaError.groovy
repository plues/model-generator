package de.hhu.stups.plues.data

/**
 * Created by David Schneider on 10.02.15.
 */
class IncompatibleSchemaError extends Exception {
    public IncompatibleSchemaError(String message) {
        super(message)
    }
}
