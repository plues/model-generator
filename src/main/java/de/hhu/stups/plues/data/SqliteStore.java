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
import de.hhu.stups.plues.data.entities.ModuleLevel;
import de.hhu.stups.plues.data.entities.Session;
import de.hhu.stups.plues.data.entities.Unit;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Database based storage for timetable data.
 */
public class SqliteStore implements Store {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private String dbPath;
  private SessionFactory sessionFactory;

  public SqliteStore(final String dbPath) throws StoreException {
    this.init(dbPath);
  }

  private void logException(final Exception exception) {
    logger.error("an exception was thrown", exception);
  }

  @Override
  public synchronized void init() throws StoreException {
    assert this.dbPath != null;
    try {
      openDataBase(dbPath);
      checkSchemaVersion();
    } catch (final ClassNotFoundException | IncompatibleSchemaError exception) {
      this.close();
      logException(exception);
      throw new StoreException(exception);
    }
  }

  @Override
  public synchronized void init(final String dbpath) throws StoreException {
    this.dbPath = dbpath;
    this.init();
  }



  private synchronized <T> T getBy(final String field, final Object key, final Class<T> type) {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();

    final CriteriaBuilder cb = session.getCriteriaBuilder();

    final CriteriaQuery<T> query = cb.createQuery(type);

    final Root<T> root = query.from(type);
    query.where(cb.equal(root.get(field), key));

    final T result = session.createQuery(query).setCacheable(true).getSingleResult();
    tx.commit();

    return result;

  }

  @SuppressWarnings("unused")
  private <T> T getById(final Integer key, final Class<T> type) {
    return getBy("id", key, type);
  }

  @SuppressWarnings("unused")
  private <T> T getByKey(final String key, final Class<T> type) {
    return getBy("key", key, type);
  }

  @Override
  public synchronized String getInfoByKey(final String key) {
    return getByKey(key, Info.class).getValue();
  }

  @Override
  public synchronized Course getCourseByKey(final String key) {
    return getByKey(key, Course.class);
  }

  @Override
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

  @Override
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

  @Override
  public synchronized List<AbstractUnit> getAbstractUnitsWithoutUnits() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();

    final CriteriaBuilder cb = session.getCriteriaBuilder();
    final CriteriaQuery<AbstractUnit> query =
        cb.createQuery(AbstractUnit.class);

    final Root<AbstractUnit> root = query.from(AbstractUnit.class);
    query.where(cb.isEmpty(root.get("units")));

    final List<AbstractUnit> result = session.createQuery(query)
        .setCacheable(true).list();
    tx.commit();

    return result;
  }

  @Override
  public synchronized AbstractUnit getAbstractUnitById(final Integer key) {
    return getById(key, AbstractUnit.class);
  }

  @Override
  public synchronized Group getGroupById(final Integer gid) {
    return getById(gid, Group.class);
  }

  @Override
  public synchronized Module getModuleById(final Integer mid) {
    return getById(mid, Module.class);
  }

  @Override
  public synchronized Unit getUnitById(final Integer uid) {
    return getById(uid, Unit.class);
  }

  @Override
  public synchronized List<Course> getCourses() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final Query<Course> query = session.createQuery("from Course", Course.class);
    final List<Course> result = query.setCacheable(true).list();
    tx.commit();
    return result;
  }

  @Override
  public List<ModuleLevel> getModuleLevels() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final Query<ModuleLevel> query = session.createQuery("from ModuleLevel", ModuleLevel.class);
    final List<ModuleLevel> result = query.setCacheable(true).list();
    tx.commit();
    return result;
  }

  private List<Course> getCoursesFilteredByKzfa(final String kzfa) {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();

    final Transaction tx = session.beginTransaction();

    final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    final CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);

    criteriaQuery.where(
        criteriaBuilder.equal(
            criteriaBuilder.lower(criteriaQuery.from(Course.class).get("kzfa")), kzfa));
    final List<Course> result = session.createQuery(criteriaQuery).setCacheable(true).list();

    tx.commit();
    return result;
  }

  @Override
  public synchronized List<Course> getMinors() {
    return this.getCoursesFilteredByKzfa("n");
  }


  @Override
  public synchronized List<Course> getMajors() {
    return this.getCoursesFilteredByKzfa("h");
  }

  @Override
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

  @Override
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

  @Override
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

  @Override
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

  @Override
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

  @Override
  public synchronized List<Session> getSessions() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final Query<Session> query = session.createQuery("from Session", Session.class);
    final List<Session> result = query.setCacheable(true).list();
    tx.commit();
    return result;
  }

  /**
   * Find a session using a given Id. Return this found session.
   *
   * @param id Id of the session
   * @return Found session
   */
  @Override
  public synchronized Session getSessionById(final int id) {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final Session result = session.get(Session.class, id);
    tx.commit();
    return result;
  }

  @Override
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

  @Override
  public synchronized List<Log> getLogEntries() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final List<Log> result = session.createQuery("from Log", Log.class).setCacheable(true).list();
    tx.commit();
    return result;
  }

  @Override
  public Log getLastLogEntry() {
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    final Query query = session.createQuery("from Log order by createdAt desc", Log.class);
    query.setMaxResults(1);
    final Log result = (Log) query.uniqueResult();
    tx.commit();
    return result;
  }

  /**
   * Remove the last log entry from the database table 'Log'.
   */
  @Override
  public void removeLastLogEntry() {
    final Log lastLog = getLastLogEntry();
    if (lastLog == null) {
      return;
    }
    final org.hibernate.Session session = sessionFactory.getCurrentSession();
    final Transaction tx = session.beginTransaction();
    session.delete(lastLog);
    tx.commit();
  }

  @Override
  public synchronized void moveSession(final int sessionId, final String targetDay,
                                       final Integer targetTime) {
    final Log log = new Log();
    final Session session = getSessionById(sessionId);
    log.setSource(session.getDay(), session.getTime());
    log.setTarget(targetDay, targetTime);
    log.setSession(session);

    final org.hibernate.Session s = sessionFactory.getCurrentSession();

    final Transaction tx = s.beginTransaction();

    if (!tx.isActive()) {
      tx.begin();
    }
    try {
      final Query query
          = s.createQuery("UPDATE Session SET time = :time, day = :day WHERE id = :session_id");
      query.setParameter("time", targetTime);
      query.setParameter("day", targetDay);
      query.setParameter("session_id", session.getId());
      query.executeUpdate();
      s.save(log);
      // TODO: ensure hibernate caches to be up to date
      tx.commit();
    } catch (final HibernateException exception) {
      tx.rollback();
      logException(exception);
    }
  }

  private synchronized void checkSchemaVersion() throws IncompatibleSchemaError {
    final Properties properties = new Properties();
    try {
      properties.load(Thread.currentThread()
          .getContextClassLoader()
          .getResourceAsStream("schema.properties"));
    } catch (final IOException exception) {
      logException(exception);
    }

    final String versionStr = this.getInfoByKey("schema_version");
    final String requiredVersionStr
        = properties.getProperty("schema_version", "0");

    // versions must be identical integer values
    final Integer dbVersion = Integer.valueOf(versionStr);
    final Integer requiredVersion = Integer.valueOf(requiredVersionStr);

    if (!dbVersion.equals(requiredVersion)) {
      throw new IncompatibleSchemaError(
          String.format("Expected database schema version '%d' but was '%d'.",
            requiredVersion, dbVersion));
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

  @Override
  public synchronized void close() {
    if (sessionFactory != null) {
      sessionFactory.close();
    }
  }

  @SuppressFBWarnings("DM_GC")
  @Override
  public final synchronized void clear() {
    System.gc();
  }
}
