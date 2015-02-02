package de.stups.slottool

/**
 * Created by david on 01.02.15.
 */
enum FileType {
    BMachine("b"), Prolog("prolog")

    public final name

    FileType(String name) {
        this.name = name
    }

    @Override
    def String toString() {
        this.name
    }
}
