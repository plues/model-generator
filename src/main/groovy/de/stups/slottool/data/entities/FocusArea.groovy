package de.stups.slottool.data.entities

class FocusArea {
    int id
    String name
    Date created_at
    Date updated_at
    Set<Module> modules

    def FocusArea(int id, String name, Date created_at, Date updated_at) {
        this.id = id
        this.name = name
        this.created_at = created_at
        this.updated_at = updated_at
        this.modules = new HashSet<Module>()
    }
}
