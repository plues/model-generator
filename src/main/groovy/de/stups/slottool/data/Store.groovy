package de.stups.slottool.data

import de.stups.slottool.data.dao.AbstractUnitDAO
import de.stups.slottool.data.dao.AbstractUnitUnitSemesterDAO
import de.stups.slottool.data.dao.CourseDAO
import de.stups.slottool.data.dao.CourseLevelDAO
import de.stups.slottool.data.dao.InfoDAO
import de.stups.slottool.data.dao.LevelDAO
import de.stups.slottool.data.dao.ModuleAbstractUnitSemesterDAO
import de.stups.slottool.data.dao.ModuleDAO
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
    UnitDAO unitDAO
    GroupDAO groupDAO
    InfoDAO infoDAO
    SessionDAO sessionDAO
    LevelDAO levelDAO
    AbstractUnitDAO abstractUnitDAO
    CourseLevelDAO courseLevelDAO
    ModuleAbstractUnitSemesterDAO moduleAbstractUnitSemesterDAO
    AbstractUnitUnitSemesterDAO abstractUnitUnitSemesterDAO

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
        this.levelDAO.load()
        this.moduleDAO.load()
        this.abstractUnitDAO.load()

        this.unitDAO.load()
        this.groupDAO.load()
        this.sessionDAO.load()
//
        // join DAOs
        this.courseLevelDAO.load()
        this.moduleAbstractUnitSemesterDAO.load()
        this.abstractUnitUnitSemesterDAO.load()
    }

    def setupDAOs() {
        this.infoDAO = new InfoDAO(sql)
        this.courseDAO = new CourseDAO(sql)
        this.levelDAO = new LevelDAO(sql);
        this.moduleDAO = new ModuleDAO(sql, this.levelDAO)
        this.abstractUnitDAO = new AbstractUnitDAO(sql)
        this.unitDAO = new UnitDAO(sql)
        this.groupDAO = new GroupDAO(sql, unitDAO)
        this.sessionDAO = new SessionDAO(sql, groupDAO)
        // join DAOs
        this.courseLevelDAO = new CourseLevelDAO(sql, courseDAO, levelDAO)
        this.moduleAbstractUnitSemesterDAO = new ModuleAbstractUnitSemesterDAO(sql, moduleDAO, abstractUnitDAO)
        this.abstractUnitUnitSemesterDAO = new  AbstractUnitUnitSemesterDAO(sql, abstractUnitDAO, unitDAO)
    }

    def checkSchemaVersion() {
        def properties = new Properties()
        properties.load currentThread().contextClassLoader.getResourceAsStream("schema.properties")
        def schema_version = this.infoDAO.getById('schema_version').split(".").collect {Integer.parseInt(it)}
        def required_version = properties.getProperty("schema_version").split(".").collect {Integer.parseInt(it)}

        // Major versions must match
        // minor version may be higher in database
        if ( (schema_version[0] != required_version[0]) || (schema_version[1] < required_version[1]) ) {
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
