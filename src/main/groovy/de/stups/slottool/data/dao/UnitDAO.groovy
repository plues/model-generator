package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Unit

class UnitDAO extends AbstractDAO {
    def String getTableName() {
        "units"
    }

    Map<Integer, Unit> units
    DepartmentDAO departmentDAO

    UnitDAO(def sql, DepartmentDAO departmentDAO) {
        super(sql)
        this.units = new HashMap<Integer, Unit>()
        this.departmentDAO = departmentDAO
    }
    @Override
    protected loadRow(def row) {
        def department = departmentDAO.getById(row['department_id'])
        def unit = new Unit(row['id'], row['title'], department, row['duration'],
                    Date.parse(DATE_FORMAT, row['created_at']), Date.parse(DATE_FORMAT, row['updated_at']))
        units.put(row['id'], unit)
        department.units.add(unit)
    }

    @Override
    def getById(def id) {
        return units.get(id)
    }

    def Iterator iterator() {
        return units.values().iterator()
    }
}
