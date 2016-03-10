package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Log
import groovy.sql.Sql

class LogDAO extends AbstractDAO {

    Set<String> log

    LogDAO(def sql) {
        super(sql)
        this.log= new HashSet<Log>()
    }

    @Override
    protected loadRow(def row) {
        log.add(new Log(row['session_id'] as int, row['src_day'], row['src_time'] as int,
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

    def addEntry(String session_id, String src_day, String src_time, String target_day, String target_time) {
        log.add(new Log(session_id as int, src_day, src_time as int, target_day, target_time as int, new Date()))
    }

    def persist(Sql sql) {
        for (Log l in log) {
            String query = "INSERT INTO log (session_id, src_day, src_time, target_day, target_time, created_at)" +
                    "VALUES (${l.created_at}, '${l.src_day}', ${l.src_time}, '${l.target_day}', ${l.target_time}, ${l.created_at})"
            sql.executeUpdate(query)
        }
    }
}
