package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Log
import groovy.sql.Sql

class LogDAO extends AbstractDAO {

    Map<Integer, Log> log
    Date lastSaved

    LogDAO(def sql) {
        super(sql)
        this.log= new HashMap<Integer, Log>()
        this.lastSaved = new Date()
    }

    @Override
    protected loadRow(def row) {
        log.put(row['session_id'], new Log(row['session_id'] as int, row['src_day'], row['src_time'] as int,
                row['target_day'], row['target_time'] as int, Date.parse(DATE_FORMAT, row['created_at'])))
    }

    @Override
    def getById(def id) {
        throw new UnsupportedOperationException()
    }

    @Override
    String getTableName() {
        return "log"
    }

    @Override
    Iterator iterator() {
        throw new UnsupportedOperationException()
    }

    def addEntry(int session_id, String src_day, int src_time, String target_day, int target_time) {
        if (log[session_id]) {
            updateLog(session_id, target_day, target_time)
        } else {
            log.put(session_id, new Log(session_id, src_day, src_time, target_day, target_time, new Date()))
        }
    }

    def updateLog(int session_id, String target_day, int target_time) {
        log[session_id].target_day = target_day
        log[session_id].target_time = target_time
        log[session_id].created_at = new Date()
    }

    def persist(Sql sql) {
        for (Log l in log) {
            if (l.created_at.compareTo(lastSaved) < 0) {
                continue
            }
            String query = "INSERT INTO log (session_id, src_day, src_time, target_day, target_time, created_at)" +
                    "VALUES (${l.created_at}, '${l.src_day}', ${l.src_time}, '${l.target_day}', ${l.target_time}, ${l.created_at})"
            sql.executeUpdate(query)
        }
        lastSaved = new Date()
    }
}
