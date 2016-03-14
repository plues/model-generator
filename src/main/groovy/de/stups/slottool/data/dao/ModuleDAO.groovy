package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Module

class ModuleDAO extends AbstractDAO {
    HashMap<Integer, Module> modules

    def ModuleDAO(def sql) {
        super(sql)
        this.modules = new HashMap<Integer, Module>()
    }

    @Override
    def protected loadRow(def row) {
        def module = new Module(row['id'], row['key'], row['name'], row['title'], row['pordnr'], row['credit_points'],
                        row['elective_units'], row['mandatory'] as Boolean,
                        Date.parse(DATE_FORMAT, row['created_at']), Date.parse(DATE_FORMAT, row['updated_at']))
        this.modules.put(row['id'], module)
    }

    @Override
    def getById(def id) {
        return modules.get(id)
    }

    @Override
    String getTableName() {
        "modules"
    }

    def Iterator iterator() {
        return modules.values().iterator()
    }
}
