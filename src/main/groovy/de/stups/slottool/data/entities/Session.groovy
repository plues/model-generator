package de.stups.slottool.data.entities

class Session extends Entity {
    int id
    def day
    def time
    Integer rhythm
    Integer duration
    Date created_at
    Date updated_at
    Group group

    def Session(int id, Group group, String day, Integer time, Integer rhythm, Integer duration, Date created_at, Date updated_at) {
        this.id = id
        this.day = day
        this.time = time
        this.rhythm = rhythm
        this.duration = duration
        this.created_at = created_at
        this.updated_at = updated_at
        this.group = group
    }
}
