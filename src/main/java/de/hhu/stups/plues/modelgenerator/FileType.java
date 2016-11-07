package de.hhu.stups.plues.modelgenerator;

public enum FileType {
  B_MACHINE("b", "/data.mch.twig"),
  MODULE_COMBINATION("mc", "/data.xml.twig"),
  MODULE_TREE("Modulbaum", "/xml/tree.xml.twig"),
  MODULE_DATA("Moduldaten", "/xml/data.xml.twig"),
  UNKNOWN("?", "?");

  public final String typeName;
  public final String template;

  FileType(final String typeName, final String template) {
    this.typeName = typeName;
    this.template = template;
  }

  @Override
  public String toString() {
    return this.typeName;
  }
}
