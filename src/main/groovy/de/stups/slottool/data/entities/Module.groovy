package de.stups.slottool.data.entities

class Module {
    Date updated_at
    int id
    String name
    String frequency
    Date created_at
    Set<FocusArea> focus_areas

    Module(int id, String name, String frequency, Date created_at, Date updated_at) {
        this.id = id
        this.name = name
        this.frequency = frequency
        this.created_at = created_at
        this.updated_at = updated_at
        this.focus_areas = new HashSet<FocusArea>()
    }
}
