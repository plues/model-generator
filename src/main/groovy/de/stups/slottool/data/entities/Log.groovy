package de.stups.slottool.data.entities

class Log extends Entity {
    int session_id
    String src_day
    int src_time
    String target_day
    int target_time
    Date created_at

    def Log(int session_id, String src_day, int src_time, String target_day, int target_time, Date created_at) {
        this.session_id = session_id
        this.src_day = src_day
        this.src_time = src_time
        this.target_day = target_day
        this.target_time = target_time
        this.created_at = created_at
    }
}
