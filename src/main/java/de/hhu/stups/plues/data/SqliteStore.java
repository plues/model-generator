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
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Database based storage for timetable data.
 */
public class SqliteStore extends Store {
  private final Logger logger = Logger.getLogger(getClass().getSimpleName());
  private String dbPath;
  private SessionFactory sessionFactory;

  public SqliteStore(final String dbPath) throws IncompatibleSchemaError, StoreException {
    this.init(dbPath);
  }

  public SqliteStore() {
  }

  @Override
  public synchronized void init() throws IncompatibleSchemaError, StoreException {
    assert this.dbPath != null;
    try {
      openDataBase(dbPath);
      checkSchemaVersion();
    } catch (final ClassNotFoundException | IOException exception) {
      exception.printStackTrace();
      throw new StoreException(exception);
    }
  }

  public synchronized void init(final String dbpath)
      throws IncompatibleSchemaError, StoreException {
    this.dbPath = dbpath;
    this.init();
  }

  private synchronized <T> T getById(final Integer key, final Class<T> type) {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final CriteriaBuilder cb = session.getCriteriaBuilder();

    final CriteriaQuery<T> query = cb.createQuery(type);

    final Root<T> root = query.from(type);
    query.where(cb.equal(root.get("id"), key));

    final T result = session.createQuery(query).setCacheable(true).getSingleResult();
    tx.commit();
    return result;
  }

  private synchronized <T> T getByKey(final String key, final Class<T> type) {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();

    final CriteriaBuilder cb = session.getCriteriaBuilder();

    final CriteriaQuery<T> query = cb.createQuery(type);

    final Root<T> root = query.from(type);
    query.where(cb.equal(root.get("key"), key));

    final T result = session.createQuery(query).setCacheable(true).getSingleResult();
    tx.commit();

    return result;
  }

  public synchronized String getInfoByKey(final String key) {
    return getByKey(key, Info.class).getValue();
  }

  public synchronized Course getCourseByKey(final String key) {
    return getByKey(key, Course.class);
  }

  public synchronized List<Info> getInfo() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final List<Info> result = session
        .createQuery("from Info", Info.class)
        .setCacheable(true)
        .list();
    tx.commit();
    return result;
  }

  public synchronized List<AbstractUnit> getAbstractUnits() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final List<AbstractUnit> result = session
        .createQuery("from AbstractUnit", AbstractUnit.class)
        .setCacheable(true)
        .list();
    tx.commit();
    return result;
  }

  public synchronized AbstractUnit getAbstractUnitById(final Integer key) {
    return getById(key, AbstractUnit.class);
  }

  public synchronized Group getGroupById(final Integer gid) {
    return getById(gid, Group.class);
  }

  public synchronized Module getModuleById(final Integer mid) {
    return getById(mid, Module.class);
  }

  public synchronized List<Course> getCourses() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final Query<Course> query = session.createQuery("from Course", Course.class);
    final List<Course> result = query.setCacheable(true).list();
    tx.commit();
    return result;
  }

  public synchronized List<Group> getGroups() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final List<Group> result = session
        .createQuery("from Group", Group.class)
        .setCacheable(true)
        .list();
    tx.commit();
    return result;
  }

  public synchronized List<Level> getLevels() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final List<Level> result = session
        .createQuery("from Level", Level.class)
        .setCacheable(true)
        .list();
    tx.commit();
    return result;
  }

  public synchronized List<Module> getModules() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final List<Module> result = session
        .createQuery("from Module", Module.class)
        .setCacheable(true)
        .list();
    tx.commit();
    return result;
  }

  public synchronized List<ModuleAbstractUnitSemester> getModuleAbstractUnitSemester() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final List<ModuleAbstractUnitSemester> result = session
        .createQuery("from ModuleAbstractUnitSemester", ModuleAbstractUnitSemester.class)
        .setCacheable(true)
        .list();
    tx.commit();
    return result;
  }

  public synchronized List<ModuleAbstractUnitType> getModuleAbstractUnitType() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final List<ModuleAbstractUnitType> result = session
        .createQuery("from ModuleAbstractUnitType", ModuleAbstractUnitType.class)
        .setCacheable(true)
        .list();
    tx.commit();
    return result;
  }

  public synchronized List<Session> getSessions() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final Query<Session> query = session.createQuery("from Session", Session.class);
    final List<Session> result = query.setCacheable(true).list();
    tx.commit();
    return result;
  }

  public synchronized Session getSessionById(final int id) {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final Session result = session.get(Session.class, id);
    tx.commit();
    return result;
  }

  public synchronized List<Unit> getUnits() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final List<Unit> result = session
        .createQuery("from Unit", Unit.class)
        .setCacheable(true)
        .list();
    tx.commit();
    return result;
  }

  public synchronized List<Log> getLogEntries() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final List<Log> result = session.createQuery("from Log", Log.class).setCacheable(true).list();
    tx.commit();
    return result;
  }

  public synchronized void moveSession(final Session session, final String targetDay,
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

    final org.hibernate.Session s = sessionFactory.getCurrentSession();

    final Transaction tx = s.beginTransaction();
    try {
      s.persist(session);
      s.persist(log);
      tx.commit();
    } catch (final HibernateException exception) {
      if (tx != null) {
        tx.rollback();
      }
      exception.printStackTrace();
    } finally {
      s.flush();
    }

  }

  public synchronized void checkSchemaVersion() throws IncompatibleSchemaError, IOException {
    final Properties properties = new Properties();
    properties.load(Thread.currentThread()
        .getContextClassLoader()
        .getResourceAsStream("schema.properties"));

    final String version_str = this.getInfoByKey("schema_version");

    final String[] schema_version = version_str.split("\\.");
    final String[] required_version
      = properties.getProperty("schema_version").split("\\.");

    // Major versions must match
    // minor version may be higher in database
    if ((!schema_version[0].equals(required_version[0])) || (
        Integer.parseInt(schema_version[1]) < Integer.parseInt(required_version[1]))) {
      throw new IncompatibleSchemaError("Expected database schema "
        + "version " + required_version[0] + "." + required_version[1]
        + " but was " + schema_version[0] + "." + required_version[1]);
    }

  }

  private synchronized void openDataBase(final String dbPath)
      throws ClassNotFoundException {
    Class.forName("org.sqlite.JDBC");
    final String expandedPath = dbPath.replaceFirst("^~", System.getProperty("user.home"));

    final File db = new File(expandedPath);
    final String path = db.getAbsolutePath();

    if (!(db.exists() && db.isFile())) {
      throw new IllegalArgumentException(
        path + " does not exist or is not a file.");
    }


    logger.info("trying to open " + path);
    final String url = "jdbc:sqlite:" + path;

    final Configuration conf = new Configuration();
    conf.configure();
    conf.setProperty("hibernate.connection.url", url);

    this.sessionFactory = conf.buildSessionFactory();
  }

  public synchronized void close() {
    sessionFactory.close();
  }

  @SuppressFBWarnings("DM_GC")
  public final synchronized void clear() {
    System.gc();
  }
}
