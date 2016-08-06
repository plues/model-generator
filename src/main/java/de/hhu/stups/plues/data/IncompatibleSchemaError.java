package de.hhu.stups.plues.data;

import net.sf.ehcache.util.FindBugsSuppressWarnings;

@FindBugsSuppressWarnings("RANGE_ARRAY_INDEX")
public class IncompatibleSchemaError extends Exception {
    public IncompatibleSchemaError(String message) {
        super(message);
    }
}
