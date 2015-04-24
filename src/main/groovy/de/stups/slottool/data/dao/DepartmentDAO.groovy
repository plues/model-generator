package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Department

class DepartmentDAO extends AbstractDAO{
    HashMap<Integer, Department> departments
    def String getTableName() {
        "departments"
    }

    DepartmentDAO(def sql) {
        super(sql)
        this.departments = new HashMap<Integer, Department>()
    }

    @Override
    protected loadRow(def row) {
        departments.put(row['id'],
            new Department(row['id'], row['name'], row['long_name'],
                Date.parse(DATE_FORMAT, row['created_at']), Date.parse(DATE_FORMAT, row['updated_at'])))
    }

    @Override
    def getById(def id) {
        return departments.get(id)
    }

    @Override
    Iterator iterator() {
        return this.departments.values().iterator()
    }
}
