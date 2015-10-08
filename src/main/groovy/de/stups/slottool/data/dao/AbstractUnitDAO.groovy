package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.AbstractUnit
import groovy.sql.Sql

class AbstractUnitDAO extends AbstractDAO {
    Map<Integer, AbstractUnit> abstract_units

    AbstractUnitDAO(Sql sql) {
        super(sql)
        this.abstract_units = [:]
    }

    @Override
    protected loadRow(def Object row) {
        def au = new AbstractUnit(row['id'], row['key'], row['title'], row['type'] as Character,
                Date.parse(DATE_FORMAT, row['created_at']), Date.parse(DATE_FORMAT, row['updated_at']))
        this.abstract_units[row["id"]] = au
    }

    @Override
    def getById(Object id) {
        return this.abstract_units[id]
    }

    @Override
    String getTableName() {
        return "abstract_units"
    }

    @Override
    Iterator iterator() {
        return this.abstract_units.values().iterator()
    }
}
