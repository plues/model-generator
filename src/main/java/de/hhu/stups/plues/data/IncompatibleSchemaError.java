package de.hhu.stups.plues.data;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("RANGE_ARRAY_INDEX")
class IncompatibleSchemaError extends Exception {
  IncompatibleSchemaError(final String message) {
    super(message);
  }
}
