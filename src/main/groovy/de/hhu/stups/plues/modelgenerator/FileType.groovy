package de.hhu.stups.plues.modelgenerator
enum FileType {
    BMachine("b", "mch"),
    ModuleCombination("mc", "xml"),
    Unknown("?", "?")

    public final name
    public final String extension

    private FileType(String name, String extension) {
        this.name = name
        this.extension = extension
    }

    @Override
    def String toString() {
        this.name
    }
}
