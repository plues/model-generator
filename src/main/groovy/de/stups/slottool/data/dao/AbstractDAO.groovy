package de.stups.slottool.data.dao

import groovy.sql.Sql

abstract class AbstractDAO implements Iterable{
    static DATE_FORMAT = 'yyyy-M-d H:m:s'
    Sql sql

    AbstractDAO(Sql sql) {
        this.sql = sql

    }
    def load() {
        def query = 'SELECT * from '+ tableName +';'
        this.sql.eachRow(query) { row ->
            this.loadRow(row)
        }
    }
    abstract protected loadRow(def row)

    abstract getById(Object id)

    abstract String getTableName()
}
