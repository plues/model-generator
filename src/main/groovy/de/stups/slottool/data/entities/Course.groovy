package de.stups.slottool.data.entities

class Course {
    int id
    String short_name
    String long_name
    String degree
    int po
    Date created_at
    Date updated_at
    Set<Level> levels
    String kzfa
    Set<Module> modules
    String key

    def Course(int id, String key, String long_name, String short_name, String degree, String kzfa, int po, Date created_at, Date updated_at) {
        this.id = id
        this.key = key
        this.short_name = short_name
        this.long_name = long_name
        this.degree = degree
        this.kzfa = kzfa
        this.po = po
        this.created_at = created_at
        this.updated_at = updated_at
        this.levels = new HashSet<>()
        this.modules = new HashSet<>()
    }

    def getName() {
        return "${this.degree}-${this.short_name}-${this.kzfa}-${this.po}"
    }
    def getFullName() {
        return "${this.long_name} (${this.degree} ${this.kzfa}) PO:${this.po}"
    }
}
