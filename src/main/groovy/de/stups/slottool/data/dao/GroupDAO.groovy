package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Group

class GroupDAO extends AbstractDAO {
    UnitDAO unitDAO
    Map<Integer, Group> groups

    def String getTableName() {
        "groups"
    }

    GroupDAO(def sql, UnitDAO unitDAO) {
        super(sql)
        this.groups = new HashMap<Integer, Group>()
        this.unitDAO = unitDAO
    }

    @Override
    protected loadRow(def row) {
        def group = new Group(row['id'], unitDAO.getById(row['unit_id']), row['title'],
                Date.parse(AbstractDAO.DATE_FORMAT, row['created_at']),
                Date.parse(AbstractDAO.DATE_FORMAT, row['updated_at']))
        this.groups.put(row['id'], group)
        unitDAO.getById(row['unit_id']).groups.add(group)
    }

    @Override
    def getById(def id) {
        this.groups.get(id)
    }

    def Iterator iterator() {
        return groups.values().iterator()
    }
}
