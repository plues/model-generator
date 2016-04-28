package de.stups.slottool.data

import de.stups.slottool.data.entities.AbstractUnit
import de.stups.slottool.data.entities.AbstractUnitUnitSemester
import de.stups.slottool.data.entities.Course
import de.stups.slottool.data.entities.Group
import de.stups.slottool.data.entities.Info
import de.stups.slottool.data.entities.Level
import de.stups.slottool.data.entities.Module
import de.stups.slottool.data.entities.ModuleAbstractUnitSemester
import de.stups.slottool.data.entities.Unit
import groovy.sql.Sql
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.hibernate.criterion.Restrictions

import java.nio.file.Files
import java.nio.file.Paths

import static java.lang.Thread.currentThread
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING

/**
 * Created by David Schneider on 30.01.15.
 */
class Store extends AbstractStore {
    Session session

    LinkedHashMap units
    String dbpath

    def Store(String dbpath) {
        this.dbpath = dbpath
        this.session = openDataBase(dbpath)
        checkSchemaVersion()
        Runtime.addShutdownHook {
            session.close()
        }
    }

    def List<Info> getInfo() {
        session.createQuery("from Info").setCacheable(true).list()
    }

    def List<AbstractUnit> getAbstractUnits() {
        session.createQuery("from AbstractUnit").setCacheable(false).list()
    }

    def getAbstractUnitUnitSemesterByUnitID(def unit_id) {
        this.session.createCriteria(AbstractUnitUnitSemester.class).add(Restrictions.eq("unit.id", unit_id)).list()
    }

    def List<AbstractUnitUnitSemester> getAbstractUnitUnitSemester() {
        session.createQuery("from AbstractUnitUnitSemester").setCacheable(false).list()
    }

    def List<Course> getCourses() {
        session.createQuery("from Course").setCacheable(false).list()
    }

    Course getCourseByKey(String key) {
        this.session.createCriteria(Course.class).add(Restrictions.eq("key", key)).setCacheable(false).uniqueResult() as Course
    }

    def List<Group> getGroups() {
        session.createQuery("from Group").setCacheable(false).list()
    }
    def List<Group> getGroupsByIDs(Collection<Integer> entries) {
        this.session.createCriteria(Group.class).add(Restrictions.in("id", entries)).setCacheable(false).list();
    }

    def List<Level> getLevels() {
        session.createQuery("from Level").setCacheable(false).list()
    }

    def List<Module> getModules() {
        session.createQuery("from Module").setCacheable(false).list()
    }

    def List<ModuleAbstractUnitSemester> getModuleAbstractUnitSemester() {
        session.createQuery("from ModuleAbstractUnitSemester").setCacheable(false).list()
    }

    def List<Session> getSessions() {
        session.createQuery("from Session").setCacheable(false).list()
    }

    def List<Unit> getUnits() {
        session.createQuery("from Unit").setCacheable(false).list()
    }

    def checkSchemaVersion() {
        def properties = new Properties()
        properties.load currentThread().contextClassLoader.getResourceAsStream("schema.properties")
        def version_str =session.createQuery("from Info where key = 'schema_version'").uniqueResult().value
        def schema_version = version_str.split("\\.")
        def required_version = properties.getProperty("schema_version").split("\\.")

        // Major versions must match
        // minor version may be higher in database
        if ( (schema_version[0] != required_version[0])
                || (Integer.parseInt(schema_version[1]) < Integer.parseInt(required_version[1]))) {
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
        def url = "jdbc:sqlite:"+path

        Configuration conf = new Configuration()
        conf.configure()
        conf.setProperty("hibernate.connection.url", url)
        SessionFactory sf = conf.buildSessionFactory()
        Session session = sf.openSession()
        return session
    }


    void close(sql) {
        session.close()
        sql.close()
    }

    def persist(boolean clear) {
        persist(this.session, clear)
    }

    def persist(boolean clear, File file) {
        copyDBTo(file.absolutePath)

        Sql sql = openDataBase(file.absolutePath)
        persist(sql, clear)
        this.close(sql)
    }

    def persist(Sql sql, clear_dirty_flag) {
        // apply changes
        logDAO.persist(sql)
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

    def clear() {
        session.flush()
        session.clear()
        System.gc();
    }
}
