package de.stups.slottool

/**
 * Created by david on 01.02.15.
 */
enum FileType {
    BMachine("b", "mch")

    public final name
    String extension

    FileType(String name, String extension) {
        this.name = name
        this.extension = extension
    }

    @Override
    def String toString() {
        this.name
    }
}
