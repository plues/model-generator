package de.stups.slottool.data.entities

class Unit {
    int id
    String title
    Department department
    Date created_at
    Date updated_at

    Set<Group> groups
    int duration

    def Unit(int id, String title, Department department, int duration, Date created_at, Date updated_at) {
        this.id = id
        this.title = title
        this.department = department
        this.duration = duration
        this.created_at = created_at
        this.updated_at = updated_at
        this.groups = new HashSet<Group>()
    }
}
