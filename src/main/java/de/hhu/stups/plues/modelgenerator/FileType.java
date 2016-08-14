package de.hhu.stups.plues.modelgenerator;

public enum FileType {
  BMachine("b", "mch"), ModuleCombination("mc", "xml"), Unknown("?", "?");

  public final String name;
  public final String extension;

  FileType(final String name, final String extension) {
    this.name = name;
    this.extension = extension;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
