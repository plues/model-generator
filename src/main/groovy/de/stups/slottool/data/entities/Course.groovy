package de.stups.slottool.data.entities

class Course {
    int id
    String name
    String long_name
    int elective_modules
    Date created_at
    Date updated_at

    def Course(int id, String name, String long_name, int elective_modules, Date created_at, Date updated_at) {
        this.id = id
        this.name = name
        this.long_name = long_name
        this.elective_modules = elective_modules
        this.created_at = created_at
        this.updated_at = updated_at
    }
}
