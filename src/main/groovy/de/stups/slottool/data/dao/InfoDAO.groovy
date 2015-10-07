package de.stups.slottool.data.dao

class InfoDAO extends AbstractDAO {
    Map<String, String> info
    InfoDAO(def sql) {
        super(sql)
        this.info = new HashMap<String, String>()
    }

    @Override
    protected loadRow(def row) {
        info.put(row['key'], row['value'])
    }

    @Override
    def getById(def id) {
        return info.get(id)
    }

    @Override
    String getTableName() {
        return "info"
    }

    @Override
    Iterator iterator() {
        return info.iterator()
    }
}
