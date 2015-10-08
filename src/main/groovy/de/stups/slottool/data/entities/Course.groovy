package de.stups.slottool.data.entities

class Course {
    int id
    String name
    String long_name
    String degree
    int po
    Date created_at
    Date updated_at
    Set<Level> levels

    def Course(int id, String name, String short_name, String degree, int po, Date created_at, Date updated_at) {
        this.id = id
        this.name = name
        this.long_name = short_name
        this.degree = degree
        this.po = po
        this.created_at = created_at
        this.updated_at = updated_at
        this.levels = new HashSet<>()
    }
}
