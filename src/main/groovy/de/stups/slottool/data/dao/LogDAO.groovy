package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Log
import groovy.sql.Sql

import java.sql.SQLException

class LogDAO extends AbstractDAO {

    Map<Integer, Log> log
    Map<Integer, Log> tempLog

    LogDAO(def sql) {
        super(sql)
        this.log = new HashMap<Integer, Log>()
        this.tempLog = new LinkedHashMap<Integer, Log>()
    }

    @Override
    protected loadRow(def row) {
        log.put(row['session_id'], new Log(row['session_id'] as int, row['src_day'], row['src_time'] as int,
                row['target_day'], row['target_time'] as int, Date.parse(DATE_FORMAT, row['created_at'])))
    }

    @Override
    def getById(def id) {
        return log.get(id)
    }

    @Override
    String getTableName() {
        return "log"
    }

    @Override
    Iterator iterator() {
        return log.values().iterator()
    }

    def addEntry(int session_id, String src_day, int src_time, String target_day, int target_time) {
        if (tempLog[session_id]) {
            updateLog(session_id, target_day, target_time)
        } else {
            tempLog.put(session_id, new Log(session_id, src_day, src_time, target_day, target_time, new Date()))
        }
    }

    def updateLog(int session_id, String target_day, int target_time) {
        tempLog[session_id].target_day = target_day
        tempLog[session_id].target_time = target_time
        tempLog[session_id].created_at = new Date()
    }

    def persist(Sql sql) {
        try {
            for (Log l in tempLog) {
                String query = "INSERT INTO log (session_id, src_day, src_time, target_day, target_time, created_at)" +
                        "VALUES (${l.session_id}, '${l.src_day}', ${l.src_time}, '${l.target_day}', ${l.target_time}, ${l.created_at})"
                sql.executeUpdate(query)
                log.put(l.session_id, l)
            }
            tempLog.clear()
        } catch (SQLException e) {
            print(e.stackTrace)
        }
    }
}
