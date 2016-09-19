package de.hhu.stups.plues.modelgenerator;

public enum FileType {
  B_MACHINE("b", "mch"), MODULE_COMBINATION("mc", "xml"), UNKNOWN("?", "?");

  public final String typeName;
  public final String extension;

  FileType(final String typeName, final String extension) {
    this.typeName = typeName;
    this.extension = extension;
  }

  @Override
  public String toString() {
    return this.typeName;
  }
}
