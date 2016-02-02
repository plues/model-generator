package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Unit

class UnitDAO extends AbstractDAO {
    def String getTableName() {
        "units"
    }

    Map<Integer, Unit> units

    UnitDAO(def sql) {
        super(sql)
        this.units = new HashMap<Integer, Unit>()
    }
    @Override
    protected loadRow(def row) {
        def unit = new Unit(row['id'], "${row['unit_key']}", row['title'], Date.parse(DATE_FORMAT, row['created_at']),
                                                                           Date.parse(DATE_FORMAT, row['updated_at']))
        units.put(row['id'], unit)
    }

    @Override
    def getById(def id) {
        return units.get(id)
    }

    def Iterator iterator() {
        return units.values().iterator()
    }
}
