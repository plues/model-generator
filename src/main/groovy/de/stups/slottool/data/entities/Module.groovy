package de.stups.slottool.data.entities

class Module {
    Date updated_at
    int id
    String name
    Date created_at
    String title
    Integer pordnr
    Boolean mandatory
    Course course
    Integer elective_units

    Module(Integer id, Level level, String name, String title, Integer pordnr, Integer elective_units, Boolean mandatory, Date created_at, Date updated_at) {
        this.id = id
        this.name = name
        this.title = title
        this.pordnr = pordnr
        this.elective_units = elective_units
        this.mandatory = mandatory
        this.created_at = created_at
        this.updated_at = updated_at
    }
}
