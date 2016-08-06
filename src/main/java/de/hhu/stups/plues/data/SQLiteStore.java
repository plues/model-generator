package de.hhu.stups.plues.data;

import de.hhu.stups.plues.data.entities.AbstractUnit;
import de.hhu.stups.plues.data.entities.Course;
import de.hhu.stups.plues.data.entities.Group;
import de.hhu.stups.plues.data.entities.Info;
import de.hhu.stups.plues.data.entities.Level;
import de.hhu.stups.plues.data.entities.Log;
import de.hhu.stups.plues.data.entities.Module;
import de.hhu.stups.plues.data.entities.ModuleAbstractUnitSemester;
import de.hhu.stups.plues.data.entities.ModuleAbstractUnitType;
import de.hhu.stups.plues.data.entities.Session;
import de.hhu.stups.plues.data.entities.Unit;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class SQLiteStore extends Store {
    public SQLiteStore(String dbpath) throws IncompatibleSchemaError, StoreExeception {
        this.init(dbpath);
    }

    public SQLiteStore() {
    }

    @Override
    public void init() throws IncompatibleSchemaError, StoreExeception {
        assert this.dbpath != null;
        try {
            openDataBase(dbpath);
            checkSchemaVersion();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new StoreExeception(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new StoreExeception(e);
        }
    }

    public void init(String dbpath) throws IncompatibleSchemaError, StoreExeception {
        this.dbpath = dbpath;
        this.init();
    }

    private <T> T getByID(Integer key, Class<T> type) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<T> query = cb.createQuery(type);

        Root<T> root = query.from(type);
        query.where(cb.equal(root.get("id"), key));

        return session.createQuery(query).setCacheable(true).getSingleResult();
    }

    private <T> T getByKey(String key, Class<T> type) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<T> query = cb.createQuery(type);

        Root<T> root = query.from(type);
        query.where(cb.equal(root.get("key"), key));

        return session.createQuery(query).setCacheable(true).getSingleResult();
    }

    public String getInfoByKey(String key) {
        return getByKey(key, Info.class).getValue();
    }

    public Course getCourseByKey(String key) {
        return getByKey(key, Course.class);
    }

    public List<Info> getInfo() {
        return session.createQuery("from Info").setCacheable(true).list();
    }

    public List<AbstractUnit> getAbstractUnits() {
        return session.createQuery("from AbstractUnit").setCacheable(true).list();
    }

    public AbstractUnit getAbstractUnitByID(Integer key) {
        return getByID(key, AbstractUnit.class);
    }

    public Group getGroupByID(Integer gid) {
        return getByID(gid, Group.class);
    }

    public Module getModuleByID(Integer mid) {
        return getByID(mid, Module.class);
    }

    public List<Course> getCourses() {
        return session.createQuery("from Course").setCacheable(true).list();
    }

    public List<Course> getMinors() {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Course> query = cb.createQuery(Course.class);

        Root<Course> root = query.from(Course.class);
        query.where(cb.equal(root.get("kzfa"), Course.KZFA.getMINOR()));

        return session.createQuery(query).setCacheable(true).getResultList();
    }

    public List<Course> getMajors() {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Course> query = cb.createQuery(Course.class);

        Root<Course> root = query.from(Course.class);
        query.where(cb.equal(root.get("kzfa"), Course.KZFA.getMAJOR()));

        return session.createQuery(query).setCacheable(true).getResultList();
    }

    public List<Group> getGroups() {
        return session.createQuery("from Group").setCacheable(true).list();
    }

    public List<Level> getLevels() {
        return session.createQuery("from Level").setCacheable(true).list();
    }

    public List<Module> getModules() {
        return session.createQuery("from Module").setCacheable(true).list();
    }

    public List<ModuleAbstractUnitSemester> getModuleAbstractUnitSemester() {
        return session.createQuery("from ModuleAbstractUnitSemester").setCacheable(true).list();
    }

    public List<ModuleAbstractUnitType> getModuleAbstractUnitType() {
        return session.createQuery("from ModuleAbstractUnitType").setCacheable(true).list();
    }

    public List<Session> getSessions() {
        return session.createQuery("from Session").setCacheable(true).list();
    }

    public Session getSessionByID(int id) {
        return session.get(Session.class, id);
    }

    public List<Unit> getUnits() {
        return session.createQuery("from Unit").setCacheable(true).list();
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    public List getLogEntries() {
        return session.createQuery("from Log").setCacheable(true).list();
    }

    public void moveSession(Session session, String targetDay, String targetTime) {
        String srcDay = session.getDay();
        String srcTime = session.getTime().toString();

        session.setDay(targetDay);
        session.setTime(Integer.parseInt(targetTime));

        Log log = new Log();
        // TODO day and time should each be a field in the log table
        log.setSrc(srcDay + srcTime);
        log.setTarget(targetDay + targetTime);
        log.setSession(session);

        org.hibernate.Session s = this.session;

        final Transaction tx = s.beginTransaction();
        try {
            s.persist(session);
            s.persist(log);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            s.flush();
        }

    }

    public void checkSchemaVersion() throws IncompatibleSchemaError, IOException {
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("schema.properties"));

        String version_str = this.getInfoByKey("schema_version");

        final String[] schema_version = version_str.split("\\.");
        final String[] required_version = properties.getProperty("schema_version").split("\\.");

        // Major versions must match
        // minor version may be higher in database
        if((!schema_version[0].equals(required_version[0])) || (Integer.parseInt(schema_version[1]) < Integer.parseInt(required_version[1]))) {
            throw new IncompatibleSchemaError("Expected database schema " + "version " + String.valueOf(required_version) + " but was " + String.valueOf(schema_version));
        }

    }

    private org.hibernate.Session openDataBase(String db_path) throws ClassNotFoundException {
        Class.class.forName("org.sqlite.JDBC");
        db_path = db_path.replaceFirst("^~", System.getProperty("user.home"));

        File db = new File(db_path);
        String path = db.getAbsolutePath();

        if(!(db.exists() && db.isFile())) {
            throw new IllegalArgumentException(path + " does not exist or is not a file.");
        }


        DefaultGroovyMethods.println(this, "trying to open " + path);
        String url = "jdbc:sqlite:" + path;

        Configuration conf = new Configuration();
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

    public void clear() {
        sessionFactory.getCache().evictAllRegions();
        session.clear();
        System.gc();
    }

    public org.hibernate.Session getSession() {
        return session;
    }

    public void setSession(org.hibernate.Session session) {
        this.session = session;
    }

    private String dbpath;
    private SessionFactory sessionFactory;
    private org.hibernate.Session session;
}
