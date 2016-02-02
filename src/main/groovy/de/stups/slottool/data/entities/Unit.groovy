package de.stups.slottool.data.entities

class Unit extends Entity {
    Integer id
    String title
    Date created_at
    Date updated_at
    Set<AbstractUnit> abstract_units

    Set<Group> groups
    String key

    def Unit(Integer id, String key, String title, Date created_at, Date updated_at) {
        this.id = id
        this.key = key
        this.title = title
        this.created_at = created_at
        this.updated_at = updated_at
        this.groups = new HashSet<Group>()
        this.abstract_units = new HashSet<AbstractUnit>()
    }
}
