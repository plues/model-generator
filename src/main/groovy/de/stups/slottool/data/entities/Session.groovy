package de.stups.slottool.data.entities

class Session {
    int id
    String slot
    int rhythm
    int duration
    Date created_at
    Date updated_at

    def Session(int id, Group group, String slot, int rhythm, int duration, Date created_at, Date updated_at) {
        this.id = id
        this.slot = slot
        this.rhythm = rhythm
        this.duration = duration
        this.created_at = created_at
        this.updated_at = updated_at

    }
}
