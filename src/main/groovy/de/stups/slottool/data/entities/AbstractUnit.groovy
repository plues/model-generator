package de.stups.slottool.data.entities

class AbstractUnit extends Entity {
    Integer id
    String key
    String title
    Character type
    Date created_at
    Date update_at
    Set<Module> modules

    def AbstractUnit(Integer id, String key, String title, Character type, Date created_at, Date updated_at) {
        this.id = id
        this.key = key
        this.title = title
        this.type = type
        this.created_at = created_at
        this.update_at = updated_at
        modules = new HashSet<Module>()
    }

}
