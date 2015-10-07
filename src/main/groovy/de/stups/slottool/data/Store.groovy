package de.stups.slottool.data

import de.stups.slottool.data.dao.CourseDAO
import de.stups.slottool.data.dao.CourseModuleDAO
import de.stups.slottool.data.dao.CourseModuleUnitDAO
import de.stups.slottool.data.dao.DepartmentDAO
import de.stups.slottool.data.dao.FocusAreaDAO
import de.stups.slottool.data.dao.InfoDAO
import de.stups.slottool.data.dao.ModuleDAO
import de.stups.slottool.data.dao.ModuleFocusAreaDAO
import de.stups.slottool.data.dao.SessionDAO
import de.stups.slottool.data.dao.UnitDAO
import de.stups.slottool.data.dao.GroupDAO
import groovy.sql.Sql
import static java.lang.Thread.currentThread

/**
 * Created by David Schneider on 30.01.15.
 */
class Store {
    Sql sql

    def info
    LinkedHashMap units
    CourseDAO courseDAO
    ModuleDAO moduleDAO
    FocusAreaDAO focusAreaDAO
    DepartmentDAO departmentDAO
    UnitDAO unitDAO
    GroupDAO groupDAO
    InfoDAO infoDAO
    SessionDAO sessionDAO
    CourseModuleDAO courseModuleDAO
    ModuleFocusAreaDAO moduleFocusAreaDAO
    CourseModuleUnitDAO courseModuleUnitDAO

    def Store(String dbpath) {
        this.sql = openDataBase(dbpath)
        this.setupDAOs()
        this.loadData()
        checkSchemaVersion()
        Runtime.addShutdownHook {
            sql.close()
        }
    }

    def loadData() {
        this.infoDAO.load()

        this.courseDAO.load()
        this.moduleDAO.load()
        this.focusAreaDAO.load()
        this.departmentDAO.load()
        this.unitDAO.load()
        this.groupDAO.load()
        this.sessionDAO.load()

        // join DAOs
        this.courseModuleDAO.load()
        this.moduleFocusAreaDAO.load()
        this.courseModuleUnitDAO.load()
    }

    def setupDAOs() {
        this.infoDAO = new InfoDAO(sql)
        this.courseDAO = new CourseDAO(sql)
        this.moduleDAO = new ModuleDAO(sql)
        this.focusAreaDAO = new FocusAreaDAO(sql)
        this.departmentDAO = new DepartmentDAO(sql)
        this.unitDAO = new UnitDAO(sql, departmentDAO)
        this.groupDAO = new GroupDAO(sql, unitDAO)

        this.sessionDAO = new SessionDAO(sql, groupDAO)
        // join DAOs
        this.courseModuleDAO = new CourseModuleDAO(sql, courseDAO, moduleDAO)
        this.moduleFocusAreaDAO = new ModuleFocusAreaDAO(sql, moduleDAO, focusAreaDAO)
        this.courseModuleUnitDAO = new CourseModuleUnitDAO(sql, courseDAO, moduleDAO, unitDAO)
    }

    def checkSchemaVersion() {
        def properties = new Properties()
        properties.load currentThread().contextClassLoader.getResourceAsStream("schema.properties")
        def schema_version = this.infoDAO.getById('schema_version')
        def required_version = properties.getProperty("schema_version")
        if ( schema_version != required_version ) {
            throw new IncompatibleSchemaError("Expected database schema version ${required_version} but was ${schema_version}")
        }
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
