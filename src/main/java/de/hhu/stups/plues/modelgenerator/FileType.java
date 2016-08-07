package de.hhu.stups.plues.modelgenerator;

public enum FileType {
    BMachine("b", "mch"), ModuleCombination("mc", "xml"), Unknown("?", "?");

    private FileType(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }

    @Override
    public String toString() {
        return ((String) (this.name));
    }

    public final String name;
    public final String extension;
}
