package de.stups.slottool.data

import de.stups.slottool.data.dao.*
import groovy.sql.Sql
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

import java.nio.file.Files
import java.nio.file.Paths

import static java.lang.Thread.currentThread
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING

/**
 * Created by David Schneider on 30.01.15.
 */
class Store {
    Session session

    def info
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
}
