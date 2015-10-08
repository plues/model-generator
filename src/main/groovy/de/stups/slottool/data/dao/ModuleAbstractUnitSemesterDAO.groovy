package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.ModuleAbstractUnitSemester
import groovy.sql.Sql

class ModuleAbstractUnitSemesterDAO extends AbstractDAO {
    ModuleDAO moduleDAO
    AbstractUnitDAO abstractUnitDAO
    HashSet<ModuleAbstractUnitSemester> data

    def ModuleAbstractUnitSemesterDAO(Sql sql, ModuleDAO moduleDAO, AbstractUnitDAO abstractUnitDAO) {
        super(sql)
        this.moduleDAO = moduleDAO
        this.abstractUnitDAO = abstractUnitDAO
        this.data = new HashSet<ModuleAbstractUnitSemester>()
    }

    @Override
    protected loadRow(def Object row) {
        def au = abstractUnitDAO.getById(row['abstract_unit_id'])
        def module = moduleDAO.getById(row['module_id'])
        def item = new ModuleAbstractUnitSemester(module, au, row['semester'])
        this.data.add(item)
    }

    @Override
    def getById(Object id) {
        throw new UnsupportedOperationException()

    }

    @Override
    String getTableName() {
        return "modules_abstract_units_semesters"
    }

    @Override
    Iterator iterator() {
        return this.data.iterator()
    }
}
