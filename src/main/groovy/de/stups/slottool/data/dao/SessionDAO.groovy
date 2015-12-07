package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Session
import groovy.sql.Sql

class SessionDAO extends AbstractDAO{
    Map<Integer, Session> sessions;
    GroupDAO groupDAO

    SessionDAO(def sql, GroupDAO groupDAO) {
        super(sql)
        this.groupDAO = groupDAO
        this.sessions = new HashMap<Integer, Session>()
    }

    @Override
    protected loadRow(def Object row) {
        def group = groupDAO.getById(row['group_id'])
        def session = new Session(row['id'], group, row['day'], row['time'], row['rhythm'], row['duration'],
                            Date.parse(DATE_FORMAT, row['created_at']), Date.parse(DATE_FORMAT, row['updated_at']))
        sessions.put(row['id'], session)
        group.sessions.add(session)

    }

    @Override
    def getById(def id) {
        return this.sessions.get(id)
    }

    @Override
    String getTableName() {
        return "sessions"
    }

    @Override
    Iterator iterator() {
        return sessions.values().iterator()
    }

    // XXX Add to AbstractDAO
    @SuppressWarnings("GroovyUnusedDeclaration")
    def update(Session session) {
        this.update(session, this.sql);
    }

    def update(Session session, Sql sql) {
        def fields = [day: session.day, time: session.time]
        super.update(sql, session.id, fields)
    }
}
