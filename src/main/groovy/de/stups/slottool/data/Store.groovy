package de.stups.slottool.data

import groovy.sql.Sql
/**
 * Created by David Schneider on 30.01.15.
 */
class Store {
    Sql sql

    def info
    LinkedHashMap units

    def Store(String dbpath) {
        this.sql = openDataBase(dbpath)
        Runtime.addShutdownHook {
            sql.close()
        }
    }

    public getMajorModuleRequirement() {
        this.sql.rows("SELECT course, requirement from major_module_requirements;")
    }

    public getFocusAreas() {
        this.sql.rows("SELECT id,name from focus_areas;")
    }

    public String getModelInfo(String value) {
        if( ! this.info ) {
            this.info = [:]
            this.sql.eachRow("SELECT * FROM info") { row ->
                info[row['key']] = row['value']
            }
        }
        return this.info[value]
    }

    public getModules() {
        this.sql.rows("SELECT name, frequency FROM modules ORDER by name ASC;")
    }

    public getCourses() {
        this.sql.rows("SELECT name, long_name FROM courses;")
    }

    public getDepartments() {
        this.sql.rows("SELECT name, long_name FROM departments;")
    }

    public getSessions() {
        this.sql.rows("SELECT id, slot, rhythm, duration from sessions;")
    }

    public getMapping() {
        this.sql.rows("SELECT * from mapping;")
    }

    public getUnits() {
        if ( this.units ) {
            return this.units
        }
        def units = [:]
        this.sql.eachRow("SELECT units.id AS unit_id, units.title AS unit_title, units.duration AS unit_duration, units.department AS unit_department, groups.id AS group_id, groups.title AS group_title, group_sessions.session_id AS session_id FROM units JOIN groups ON units.id = groups.unit_id JOIN group_sessions ON groups.id = group_sessions.group_id ORDER BY unit_id") { row ->
            def unit = units[row['unit_id']]
            if ( ! unit ) {
                unit = units[row['unit_id']] = [title: row['unit_title'], department: row['unit_department'], duration: row['unit_duration'], groups: [:]]
            }
            def groups = unit['groups']
            def group = groups[row['group_id']]
            if ( ! group ) {
                group = groups[row['group_id']] = [title: row['group_title'], sessions: []]
            }
            group['sessions'].add(row['session_id'])
        }
        this.units = units
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    public updateSession(idx, session) {
        def fields = ['updated_at=datetime(\'now\')']
        for (attr in session) {
            if (attr.key == 'id') {
                continue
            }
            fields.add("\"${attr.key}\" = \"${attr.value}\"")
        }
        def query = "UPDATE sessions SET ${fields.join(", ")} WHERE id = ${idx};".toString() // sigh...
        this.sql.executeUpdate(query)
    }

    private openDataBase(String db_path) {
        Class.forName("org.sqlite.JDBC");
        db_path = db_path.replaceFirst("^~", System.getProperty("user.home"));

        def db = new File(db_path)
        def path = db.getAbsolutePath()

        if ( ! (db.exists() && db.isFile())) {
            throw new IllegalArgumentException(path + " does not exist or is not a file.")
        }

        println("trying to open " + path)
        Sql.newInstance("jdbc:sqlite:"+path)
    }


    @SuppressWarnings("GroovyUnusedDeclaration")
    void close() {
        this.sql.close()
    }
}
