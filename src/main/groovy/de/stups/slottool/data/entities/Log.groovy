package de.stups.slottool.data.entities

class Log extends Entity {
    int session_id
    String src
    String target
    Date created_at

    def Log(int session_id, String src, String target, Date created_at) {
        this.session_id = session_id
        this.src = src
        this.target = target
        this.created_at = created_at
    }
}
