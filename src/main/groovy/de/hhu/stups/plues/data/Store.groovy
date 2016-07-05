package de.hhu.stups.plues.data

import de.hhu.stups.plues.data.entities.AbstractUnit
import de.hhu.stups.plues.data.entities.Course
import de.hhu.stups.plues.data.entities.Group
import de.hhu.stups.plues.data.entities.Info
import de.hhu.stups.plues.data.entities.Level
import de.hhu.stups.plues.data.entities.Log
import de.hhu.stups.plues.data.entities.Module
import de.hhu.stups.plues.data.entities.ModuleAbstractUnitSemester
import de.hhu.stups.plues.data.entities.Session
import de.hhu.stups.plues.data.entities.Unit
import de.stups.slottool.data.entities.*
import org.hibernate.HibernateException
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.hibernate.cfg.Configuration
import org.hibernate.criterion.Restrictions

import static java.lang.Thread.currentThread

class Store extends AbstractStore {

    String dbpath
    SessionFactory sessionFactory
    org.hibernate.Session session

    def Store(String dbpath) {
        this.init(dbpath);
    }

    def Store() {
    }

    @Override
    void init() {
        assert this.dbpath != null
        openDataBase(dbpath)
        checkSchemaVersion()
    }

    void init(String dbpath) {
        this.dbpath = dbpath;
        this.init();
    }

    def List<Info> getInfo() {
        session.createQuery("from Info").setCacheable(true).list()
    }

    def List<AbstractUnit> getAbstractUnits() {
        session.createQuery("from AbstractUnit").setCacheable(true).list()
    }

    def AbstractUnit getAbstractUnitByID(Integer key) {
        session.createCriteria(AbstractUnit.class).add(Restrictions.eq("id", key)).setCacheable(true).uniqueResult() as AbstractUnit
    }

    def Group getGroupByID(Integer gid) {
        session.createCriteria(Group.class).add(Restrictions.eq("id", gid)).setCacheable(true).uniqueResult() as Group
    }

    Module getModuleByID(Integer mid) {
        session.createCriteria(Module.class).add(Restrictions.eq("id", mid)).setCacheable(true).uniqueResult() as Module
    }

    def List<Course> getCourses() {
        session.createQuery("from Course").setCacheable(true).list()
    }

    Course getCourseByKey(String key) {
        session.createCriteria(Course.class).add(Restrictions.eq("key", key)).setCacheable(true).uniqueResult() as Course
    }

    def List<Course> getMinors() {
        session.createCriteria(Course.class).add(Restrictions.eq("kzfa", Course.KZFA.MINOR)).setCacheable(true).list()
    }

    def List<Course> getMajors() {
        session.createCriteria(Course.class).add(Restrictions.eq("kzfa", Course.KZFA.MAJOR)).setCacheable(true).list()
    }

    def List<Group> getGroups() {
        session.createQuery("from Group").setCacheable(true).list()
    }

    def List<Level> getLevels() {
        session.createQuery("from Level").setCacheable(true).list()
    }

    def List<Module> getModules() {
        session.createQuery("from Module").setCacheable(true).list()
    }

    def List<ModuleAbstractUnitSemester> getModuleAbstractUnitSemester() {
        session.createQuery("from ModuleAbstractUnitSemester").setCacheable(true).list()
    }

    def List<Session> getSessions() {
        session.createQuery("from Session").setCacheable(true).list()
    }
    def Session getSessionByID(int id) {
        session.get(Session, id)
    }

    def List<Unit> getUnits() {
        session.createQuery("from Unit").setCacheable(true).list()
    }


    def getLogEntries() {
        session.createQuery("from Log").setCacheable(true).list()
    }

    def moveSession(Session session, String target_day, String target_time) {
        String src_day = session.getDay()
        String src_time = session.getTime()
        session.day = target_day
        session.time = target_time.toInteger()

        Log log = new Log();
        // TODO day and time should each be a field in the log table
        log.setSrc(src_day+src_time);
        log.setTarget(target_day+target_time)
        log.setSession(session);

        Transaction tx;
        def s = this.session

        try{
            tx = s.beginTransaction();
            s.persist(session);
            s.persist(log);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
           s.flush()
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
        this.sessionFactory = conf.buildSessionFactory()
        this.session = sessionFactory.openSession()
    }

    void close() {
        this.clear()
        session.close()
        sessionFactory.close()
    }

    void clear() {
        sessionFactory.cache.evictAllRegions()
        session.flush()
        session.clear()
        System.gc();
    }
}
