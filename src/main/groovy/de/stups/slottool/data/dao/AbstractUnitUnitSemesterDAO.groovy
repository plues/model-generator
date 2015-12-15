package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.AbstractUnitUnitSemester
import groovy.sql.Sql

/**
 * Created by david on 08.10.15.
 */
class AbstractUnitUnitSemesterDAO extends AbstractDAO {
    AbstractUnitDAO abstractUnitDAO
    UnitDAO unitDAO
    HashSet<AbstractUnitUnitSemester> data

    AbstractUnitUnitSemesterDAO(Sql sql, AbstractUnitDAO abstractUnitDAO, UnitDAO unitDAO) {
        super(sql)
        this.abstractUnitDAO = abstractUnitDAO
        this.unitDAO = unitDAO
        this.data = new HashSet<AbstractUnitUnitSemester>()
    }

    @Override
    protected loadRow(def Object row) {
        def unit = unitDAO.getById(row['unit_id'])
        def au = abstractUnitDAO.getById(row['abstract_unit_id'])
        def item = new AbstractUnitUnitSemester(au, unit, row['semester'])
        this.data.add(item)
        unit.abstract_units.add(au)
    }

    @Override
    def getById(Object id) {
        throw new UnsupportedOperationException()
    }

    @Override
    String getTableName() {
        return "unit_abstract_unit_semester"
    }

    @Override
    Iterator iterator() {
        return this.data.iterator()
    }
}
