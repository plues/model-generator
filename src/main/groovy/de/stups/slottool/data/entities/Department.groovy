package de.stups.slottool.data.entities

class Department {
    int id
    String name
    String long_name
    Date created_at
    Date updated_at

    Set<Unit> units

    Department(int id, String name, String long_name, Date created_at, Date updated_at) {
        this.id = id
        this.name = name
        this.long_name = long_name
        this.created_at = created_at
        this.updated_at = updated_at

        this.units = new HashSet<Unit>()

    }
}
