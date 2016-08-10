package de.hhu.stups.plues.data;

import de.hhu.stups.plues.data.entities.*;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SQLiteStore extends Store {
    public SQLiteStore(final String dbpath) throws IncompatibleSchemaError, StoreException {
        this.init(dbpath);
    }

    public SQLiteStore() {
    }

    @Override
    public void init() throws IncompatibleSchemaError, StoreException {
        assert this.dbpath != null;
        try {
            openDataBase(dbpath);
            checkSchemaVersion();
        } catch (final ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new StoreException(e);
        }
    }

    public void init(final String dbpath) throws IncompatibleSchemaError, StoreException {
        this.dbpath = dbpath;
        this.init();
    }

    private <T> T getByID(final Integer key, final Class<T> type) {
        final CriteriaBuilder cb = session.getCriteriaBuilder();

        final CriteriaQuery<T> query = cb.createQuery(type);

        final Root<T> root = query.from(type);
        query.where(cb.equal(root.get("id"), key));

        return session.createQuery(query).setCacheable(true).getSingleResult();
    }

    private <T> T getByKey(final String key, final Class<T> type) {
        final CriteriaBuilder cb = session.getCriteriaBuilder();

        final CriteriaQuery<T> query = cb.createQuery(type);

        final Root<T> root = query.from(type);
        query.where(cb.equal(root.get("key"), key));

        return session.createQuery(query).setCacheable(true).getSingleResult();
    }

    public String getInfoByKey(final String key) {
        return getByKey(key, Info.class).getValue();
    }

    public Course getCourseByKey(final String key) {
        return getByKey(key, Course.class);
    }

    public List<Info> getInfo() {
        return session.createQuery("from Info", Info.class)
                .setCacheable(true).list();
    }

    public List<AbstractUnit> getAbstractUnits() {
        return session.createQuery("from AbstractUnit", AbstractUnit.class)
                .setCacheable(true).list();
    }

    public AbstractUnit getAbstractUnitByID(final Integer key) {
        return getByID(key, AbstractUnit.class);
    }

    public Group getGroupByID(final Integer gid) {
        return getByID(gid, Group.class);
    }

    public Module getModuleByID(final Integer mid) {
        return getByID(mid, Module.class);
    }

    public List<Course> getCourses() {
        return session.createQuery("from Course", Course.class)
                .setCacheable(true).list();
    }

    public List<Group> getGroups() {
        return session.createQuery("from Group", Group.class)
                .setCacheable(true).list();
    }

    public List<Level> getLevels() {
        return session.createQuery("from Level", Level.class)
                .setCacheable(true).list();
    }

    public List<Module> getModules() {
        return session.createQuery("from Module", Module.class)
                .setCacheable(true).list();
    }

    public List<ModuleAbstractUnitSemester> getModuleAbstractUnitSemester() {
        return session
                .createQuery("from ModuleAbstractUnitSemester",
                        ModuleAbstractUnitSemester.class)
                .setCacheable(true).list();
    }

    public List<ModuleAbstractUnitType> getModuleAbstractUnitType() {
        return session
                .createQuery("from ModuleAbstractUnitType",
                        ModuleAbstractUnitType.class)
                .setCacheable(true).list();
    }

    public List<Session> getSessions() {
        return session.createQuery("from Session", Session.class)
                .setCacheable(true).list();
    }

    public Session getSessionByID(final int id) {
        return session.get(Session.class, id);
    }

    public List<Unit> getUnits() {
        return session.createQuery("from Unit", Unit.class)
                .setCacheable(true).list();
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    public List getLogEntries() {
        return session.createQuery("from Log").setCacheable(true).list();
    }

    public void moveSession(final Session session, final String targetDay,
                            final String targetTime) {
        final String srcDay = session.getDay();
        final String srcTime = session.getTime().toString();

        session.setDay(targetDay);
        session.setTime(Integer.parseInt(targetTime));

        final Log log = new Log();
        // TODO day and time should each be a field in the log table
        log.setSrc(srcDay + srcTime);
        log.setTarget(targetDay + targetTime);
        log.setSession(session);

        final org.hibernate.Session s = this.session;

        final Transaction tx = s.beginTransaction();
        try {
            s.persist(session);
            s.persist(log);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            s.flush();
        }

    }

    public void checkSchemaVersion() throws IncompatibleSchemaError, IOException {
        final Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("schema.properties"));

        final String version_str = this.getInfoByKey("schema_version");

        final String[] schema_version = version_str.split("\\.");
        final String[] required_version = properties.getProperty("schema_version").split("\\.");

        // Major versions must match
        // minor version may be higher in database
        if ((!schema_version[0].equals(required_version[0])) || (Integer.parseInt(schema_version[1]) < Integer.parseInt(required_version[1]))) {
            throw new IncompatibleSchemaError("Expected database schema "
                    + "version " + Arrays.toString(required_version)
                    + " but was " + String.valueOf(schema_version));
        }

    }

    private org.hibernate.Session openDataBase(String db_path) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        db_path = db_path.replaceFirst("^~", System.getProperty("user.home"));

        final File db = new File(db_path);
        final String path = db.getAbsolutePath();

        if (!(db.exists() && db.isFile())) {
            throw new IllegalArgumentException(path + " does not exist or is not a file.");
        }


        System.out.println("trying to open " + path);
        final String url = "jdbc:sqlite:" + path;

        final Configuration conf = new Configuration();
        conf.configure();
        conf.setProperty("hibernate.connection.url", url);
        this.sessionFactory = conf.buildSessionFactory();
        return this.session = sessionFactory.openSession();
    }

    public void close() {
        this.clear();
        session.close();
        sessionFactory.close();
    }

    @SuppressFBWarnings("DM_GC")
    public void clear() {
        sessionFactory.getCache().evictAllRegions();
        session.clear();
        System.gc();
    }

    public org.hibernate.Session getSession() {
        return session;
    }

    public void setSession(final org.hibernate.Session session) {
        this.session = session;
    }

    private String dbpath;
    private SessionFactory sessionFactory;
    private org.hibernate.Session session;
}
