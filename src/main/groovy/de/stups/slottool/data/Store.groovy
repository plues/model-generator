package de.stups.slottool.data
import de.stups.slottool.data.entities.*
import org.hibernate.HibernateException
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.hibernate.cfg.Configuration
import org.hibernate.criterion.Restrictions

import static java.lang.Thread.currentThread

class Store extends AbstractStore {

    LinkedHashMap units
    String dbpath
    SessionFactory sessionFactory
    org.hibernate.Session session

    def Store(String dbpath) {
        this.dbpath = dbpath
        openDataBase(dbpath)
        checkSchemaVersion()
    }

    def List<Info> getInfo() {
        session.createQuery("from Info").setCacheable(true).list()
    }

    def List<AbstractUnit> getAbstractUnits() {
        session.createQuery("from AbstractUnit").setCacheable(false).list()
    }

    def getAbstractUnitUnitSemesterByUnitID(def unit_id) {
        session.createCriteria(AbstractUnitUnitSemester.class).add(Restrictions.eq("unit.id", unit_id)).list()
    }

    def List<AbstractUnitUnitSemester> getAbstractUnitUnitSemester() {
        session.createQuery("from AbstractUnitUnitSemester").setCacheable(false).list()
    }

    def List<Course> getCourses() {
        session.createQuery("from Course").setCacheable(false).list()
    }

    Course getCourseByKey(String key) {
        session.createCriteria(Course.class).add(Restrictions.eq("key", key)).setCacheable(false).uniqueResult() as Course
    }

    def List<Course> getMinors() {
        session.createCriteria(Course.class).add(Restrictions.eq("kzfa", Course.KZFA.MINOR)).setCacheable(false).list()
    }

    def List<Course> getMajors() {
        session.createCriteria(Course.class).add(Restrictions.eq("kzfa", Course.KZFA.MAJOR)).setCacheable(false).list()
    }

    def List<Group> getGroups() {
        session.createQuery("from Group").setCacheable(false).list()
    }

    def List<Group> getGroupsByIDs(Collection<Integer> entries) {
        session.createCriteria(Group.class).add(Restrictions.in("id", entries)).setCacheable(false).list();
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

    def List<de.stups.slottool.data.entities.Session> getSessions() {
        session.createQuery("from Session").setCacheable(false).list()
    }
    def de.stups.slottool.data.entities.Session getSessionByID(int id) {
        session.createCriteria(de.stups.slottool.data.entities.Session.class).add(Restrictions.eq("id", id)).setCacheable(false).uniqueResult() as de.stups.slottool.data.entities.Session
    }

    def List<Unit> getUnits() {
        session.createQuery("from Unit").setCacheable(false).list()
    }

    def getLogEntries() {
        session.createQuery("from Log").setCacheable(true).list()
    }

    def moveSession(de.stups.slottool.data.entities.Session session, String target_day, String target_time) {
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

    def close() {
        this.clear()
        session.close()
        sessionFactory.close()
    }
    def clear() {
        session.flush()
        session.clear()
        System.gc();
    }

}
