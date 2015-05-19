package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.FocusArea
import groovy.sql.Sql

class FocusAreaDAO extends AbstractDAO {
    Map<Integer, FocusArea> focus_areas
    def String getTableName() {
        "focus_areas"
    }

    FocusAreaDAO(Sql sql) {
        super(sql)
        this.focus_areas = new HashMap<Integer, FocusArea>();
    }

    @Override
    def protected loadRow(def row) {
        this.focus_areas.put(row['id'],
            new FocusArea(row['id'], row['name'],
                Date.parse(DATE_FORMAT, row['created_at']), Date.parse(DATE_FORMAT, row['updated_at'])))
    }

    @Override
    def getById(def id) {
        return focus_areas.get(id)
    }

    def Iterator iterator() {
       return this.focus_areas.values().iterator()
    }
}
