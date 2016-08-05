package de.hhu.stups.plues.data

import de.hhu.stups.plues.data.entities.*
import org.hibernate.HibernateException
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.hibernate.cfg.Configuration

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

import static java.lang.Thread.currentThread

class SQLiteStore extends Store {

    String dbpath
    SessionFactory sessionFactory
    org.hibernate.Session session

    def SQLiteStore(String dbpath) {
        this.init(dbpath);
    }

    def SQLiteStore() {
    }

    @Override
    void init() throws IncompatibleSchemaError {
        assert this.dbpath != null
        openDataBase(dbpath)
        checkSchemaVersion()
    }

    void init(String dbpath) throws IncompatibleSchemaError {
        this.dbpath = dbpath;
        this.init();
    }

    private <T> T getByID(Integer key, Class<T> type) {
        CriteriaBuilder cb = session.criteriaBuilder

        CriteriaQuery<T> query = cb.createQuery(type)

        Root<T> root = query.from(type)
        query.where(cb.equal(root.get("id"), key))

        session.createQuery(query).setCacheable(true).getSingleResult()
    }


    private <T> T getByKey(String key, Class<T> type) {
        CriteriaBuilder cb = session.criteriaBuilder

        CriteriaQuery<T> query = cb.createQuery(type)

        Root<T> root = query.from(type)
        query.where(cb.equal(root.get("key"), key))

        session.createQuery(query).setCacheable(true).getSingleResult()
    }

    def String getInfoByKey(String key) {
        getByKey(key, Info.class)
    }

    Course getCourseByKey(String key) {
        getByKey(key, Course.class)
    }


    def List<Info> getInfo() {
        session.createQuery("from Info").setCacheable(true).list()
    }

    def List<AbstractUnit> getAbstractUnits() {
        session.createQuery("from AbstractUnit").setCacheable(true).list()
    }


    def AbstractUnit getAbstractUnitByID(Integer key) {
        getByID(key, AbstractUnit.class)
    }

    def Group getGroupByID(Integer gid) {
        getByID(gid, Group.class)
    }

    Module getModuleByID(Integer mid) {
        getByID(mid, Module.class)
    }

    def List<Course> getCourses() {
        session.createQuery("from Course").setCacheable(true).list()
    }

    def List<Course> getMinors() {
        CriteriaBuilder cb = session.criteriaBuilder

        CriteriaQuery<Course> query = cb.createQuery(Course.class)

        Root<Course> root = query.from(Course.class)
        query.where(cb.equal(root.get("kzfa"), Course.KZFA.MINOR))

        session.createQuery(query).setCacheable(true).getResultList()
    }

    def List<Course> getMajors() {
        CriteriaBuilder cb = session.criteriaBuilder

        CriteriaQuery<Course> query = cb.createQuery(Course.class)

        Root<Course> root = query.from(Course.class)
        query.where(cb.equal(root.get("kzfa"), Course.KZFA.MAJOR))

        session.createQuery(query).setCacheable(true).getResultList()
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
        session.createQuery("from ModuleAbstractUnitSemester")
                .setCacheable(true).list()
    }

    List<ModuleAbstractUnitType> getModuleAbstractUnitType() {
        session.createQuery("from ModuleAbstractUnitType")
                .setCacheable(true).list()
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


    @SuppressWarnings("GroovyUnusedDeclaration")
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
        log.setSrc(src_day + src_time);
        log.setTarget(target_day + target_time)
        log.setSession(session);

        Transaction tx;
        def s = this.session

        try {
            tx = s.beginTransaction();
            s.persist(session);
            s.persist(log);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            s.flush()
        }
    }

    def checkSchemaVersion()
            throws IncompatibleSchemaError {
        def properties = new Properties()
        properties.load(currentThread()
                .contextClassLoader.getResourceAsStream("schema.properties"))

        String version_str = session
                .createQuery("from Info where key = 'schema_version'")
                .uniqueResult().value

        String schema_version = version_str.split("\\.")
        def required_version = properties
                .getProperty("schema_version").split("\\.")

        // Major versions must match
        // minor version may be higher in database
        if ((schema_version[0] != required_version[0])
                || (Integer.parseInt(schema_version[1])
                        < Integer.parseInt(required_version[1]))) {
            throw new IncompatibleSchemaError("Expected database schema " +
                    "version ${required_version} but was ${schema_version}")
        }
    }

    private openDataBase(String db_path) {
        Class.forName("org.sqlite.JDBC");
        db_path = db_path.replaceFirst("^~", System.getProperty("user.home"));

        def db = new File(db_path)
        def path = db.getAbsolutePath()

        if (!(db.exists() && db.isFile())) {
            throw new IllegalArgumentException(path
                    + " does not exist or is not a file.")
        }

        println("trying to open " + path)
        def url = "jdbc:sqlite:" + path

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
        session.clear()
        System.gc();
    }
}
