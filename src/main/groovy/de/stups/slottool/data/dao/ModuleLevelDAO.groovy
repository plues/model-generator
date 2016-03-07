package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Level
import de.stups.slottool.data.entities.Module
import groovy.sql.Sql

class ModuleLevelDAO extends AbstractDAO{

    ModuleDAO moduleDAO
    LevelDAO levelDAO

    def ModuleLevelDAO(Sql sql, def moduleDAO, def levelDAO) {
        super(sql)
        this.moduleDAO = moduleDAO
        this.levelDAO = levelDAO
    }

    @Override
    def protected loadRow(def row) {
        Module module = moduleDAO.getById(row['module_id'])
        Level level = levelDAO.getById(row['level_id'])
        level.modules.add(module)
    }

    @Override
    def getById(def id) {
        throw new UnsupportedOperationException()
    }

    @Override
    String getTableName() {
        "module_levels"
    }

    def Iterator iterator() {
        throw new UnsupportedOperationException()
    }
}
