package de.hhu.stups.plues.data;

import net.sf.ehcache.util.FindBugsSuppressWarnings;

@FindBugsSuppressWarnings("RANGE_ARRAY_INDEX")
class IncompatibleSchemaError extends Exception {
  IncompatibleSchemaError(final String message) {
    super(message);
  }
}
