package de.hhu.stups.plues.modelgenerator;

public enum FileType {
  BMachine("b", "mch"), ModuleCombination("mc", "xml"), Unknown("?", "?");

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
