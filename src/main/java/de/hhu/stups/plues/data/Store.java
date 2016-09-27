package de.hhu.stups.plues.data;

import de.hhu.stups.plues.data.entities.AbstractUnit;
import de.hhu.stups.plues.data.entities.Course;
import de.hhu.stups.plues.data.entities.Group;
import de.hhu.stups.plues.data.entities.Level;
import de.hhu.stups.plues.data.entities.Log;
import de.hhu.stups.plues.data.entities.Module;
import de.hhu.stups.plues.data.entities.ModuleAbstractUnitSemester;
import de.hhu.stups.plues.data.entities.ModuleAbstractUnitType;
import de.hhu.stups.plues.data.entities.Session;
import de.hhu.stups.plues.data.entities.Unit;

import java.util.List;

public interface Store {
  void init() throws IncompatibleSchemaError, StoreException;

  void init(String dbpath) throws IncompatibleSchemaError, StoreException;

  void close();

  void clear();

  List getInfo();

  List<AbstractUnit> getAbstractUnits();

  List<Course> getCourses();

  Course getCourseByKey(String key);

  List<Group> getGroups();

  List<Level> getLevels();

  List<Module> getModules();

  List<ModuleAbstractUnitSemester> getModuleAbstractUnitSemester();

  List<ModuleAbstractUnitType> getModuleAbstractUnitType();

  List<Session> getSessions();

  List<Unit> getUnits();

  AbstractUnit getAbstractUnitById(Integer key);

  Group getGroupById(Integer integer);

  String getInfoByKey(String key);

  Module getModuleById(Integer mid);

  List<Log> getLog();
}
