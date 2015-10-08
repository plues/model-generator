package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Level
import groovy.sql.Sql

class LevelDAO extends AbstractDAO {

    Map<Integer, Level> levels;

    def LevelDAO(Sql sql) {
        super(sql)
        this.levels = new HashMap<Integer, Level>()
    }

    @Override
    protected loadRow(def Object row) {
        def level = new Level(row['id'], row['name'], row['tm'], row['art'], row['min'], row['max'],
                                row['parent_id'], Date.parse(DATE_FORMAT, row['created_at']), Date.parse(DATE_FORMAT, row['updated_at']))

        levels.put(level.id, level);
    }

    @Override
    def done() {
        // resolve links
        levels.each { k,level ->
            def parent = this.levels.get(level.parent_id)
            if(parent) {
                level.parent = this.levels.get(level.parent_id)
                level.parent.children.add(level)
            }
        }
    }

    @Override
    def getById(Object id) {
        return this.levels.get(id);
    }

    @Override
    String getTableName() {
        return "levels";
    }

    @Override
    String getOrderByClause() {
        "id, parent_id ASC"
    }

    @Override
    Iterator iterator() {
        return this.levels.values().iterator()
    }
}
