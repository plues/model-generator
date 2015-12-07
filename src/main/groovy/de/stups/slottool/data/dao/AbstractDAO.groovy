package de.stups.slottool.data.dao

import groovy.sql.Sql

abstract class AbstractDAO implements Iterable{
    static DATE_FORMAT = 'yyyy-M-d H:m:s'
    Sql sql


    AbstractDAO(Sql sql) {
        this.sql = sql

    }

    protected def getOrderBy() {
        if(orderByClause) {
            " ORDER BY ${orderByClause}";
        } else {
            ""
        }
    }
    def load() {
        def query = 'SELECT * from '+ tableName + orderBy + ';'
        this.sql.eachRow(query) { row ->
            this.loadRow(row)
        }
        this.done();
    }

    abstract protected loadRow(def row)

    abstract getById(Object id)

    abstract String getTableName()

    String getOrderByClause() {
        return ""
    }

    def done() {}

    def update(Sql sql, Integer idx, Map fields) {
        def values = []
        def names = []
        fields.each {
            values << it.value
            names << "${it.key} = ?"
        }
        def query = "UPDATE ${tableName} SET ${names.join(', ')}, " +
                "updated_at = datetime('now') WHERE id = ${idx}"
        sql.executeUpdate(query, values)
    }
}
