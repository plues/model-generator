package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Session

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
}
