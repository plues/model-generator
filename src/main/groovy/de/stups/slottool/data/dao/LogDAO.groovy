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
        log.put(row['session_id'] as int, new Log(row['session_id'] as int, row['src'], row['target'], Date.parse(DATE_FORMAT, row['created_at'])))
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

    def addEntry(int session_id, String src, String target) {
        if (tempLog[session_id]) {
            updateLog(session_id, target)
        } else {
            tempLog.put(session_id, new Log(session_id, src, target, new Date()))
        }
    }

    def updateLog(int session_id, String target) {
        tempLog[session_id].target = target
        tempLog[session_id].created_at = new Date()
    }

    def persist(Sql sql) {
        try {
            for (entry in tempLog) {
                Log l = entry.value
                String query = "INSERT INTO log (session_id, src, target, created_at)" +
                        "VALUES (${l.session_id}, '${l.src}', '${l.target}', '${l.created_at.toTimestamp()}')"
                sql.executeUpdate(query)
                log.put(l.session_id, l)
            }
            tempLog.clear()
        } catch (SQLException e) {
            print(e.stackTrace)
        }
    }
}
