package de.stups.slottool.data.entities

class Group extends Entity{
    int id
    Unit unit
    Date created_at
    Date update_at

    Set<Session> sessions

    def Group(int id, Unit unit, Date created_at, Date updated_at) {
        this.id = id
        this.unit = unit
        this.created_at = created_at
        this.update_at = updated_at

        this.sessions = new HashSet<Group>()
    }
}
