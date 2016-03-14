package de.stups.slottool.data

import de.stups.slottool.data.dao.*
import groovy.sql.Sql

import java.nio.file.Files
import java.nio.file.Paths

import static java.lang.Thread.currentThread
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING

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
    LogDAO logDAO
    SessionDAO sessionDAO
    LevelDAO levelDAO
    AbstractUnitDAO abstractUnitDAO
    CourseLevelDAO courseLevelDAO
    ModuleAbstractUnitSemesterDAO moduleAbstractUnitSemesterDAO
    AbstractUnitUnitSemesterDAO abstractUnitUnitSemesterDAO
    CourseModulelDAO courseModuleDAO
    String dbpath

    CourseModuleCombinationsDAO courseModulesCombinationsDAO
    ModuleLevelDAO moduleLevelsDAO


    def Store(String dbpath) {
        this.dbpath = dbpath
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
        this.logDAO.load()

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
        this.courseModuleDAO.load()
        this.moduleAbstractUnitSemesterDAO.load()
        this.moduleLevelsDAO.load()
        this.abstractUnitUnitSemesterDAO.load()

        this.courseModulesCombinationsDAO.load()
    }

    def setupDAOs() {
        this.infoDAO = new InfoDAO(sql)
        this.logDAO = new LogDAO(sql)
        this.courseDAO = new CourseDAO(sql)
        this.levelDAO = new LevelDAO(sql);
        this.moduleDAO = new ModuleDAO(sql)
        this.abstractUnitDAO = new AbstractUnitDAO(sql)
        this.unitDAO = new UnitDAO(sql)
        this.groupDAO = new GroupDAO(sql, unitDAO)
        this.sessionDAO = new SessionDAO(sql, groupDAO)
        // join DAOs
        this.courseModuleDAO = new CourseModulelDAO(sql, courseDAO, moduleDAO)
        this.courseLevelDAO = new CourseLevelDAO(sql, courseDAO, levelDAO)
        this.moduleAbstractUnitSemesterDAO = new ModuleAbstractUnitSemesterDAO(sql, moduleDAO, abstractUnitDAO)
        this.moduleLevelsDAO = new ModuleLevelDAO(sql, moduleDAO, levelDAO)
        this.abstractUnitUnitSemesterDAO = new  AbstractUnitUnitSemesterDAO(sql, abstractUnitDAO, unitDAO)

        this.courseModulesCombinationsDAO = new CourseModuleCombinationsDAO(sql, courseDAO, moduleDAO)
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


    void close(sql) {
        sql.close()
    }

    def persist(boolean clear) {
        persist(this.sql, clear)
    }

    def persist(boolean clear, File file) {
        copyDBTo(file.absolutePath)

        Sql sql = openDataBase(file.absolutePath)
        persist(sql, clear)
        this.close(sql)
    }

    def persist(Sql sql, clear_dirty_flag) {
        // apply changes
        for(session in sessionDAO) {
            if(!session.dirty) {
               continue
            }
            sessionDAO.update(session, sql)
            // XXX move to DAO (?)
            if(clear_dirty_flag) {
                session.dirty = false
            }
        }
    }

    def copyDBTo(String target) {
        Files.copy(Paths.get(dbpath), Paths.get(target), REPLACE_EXISTING)
    }
}
