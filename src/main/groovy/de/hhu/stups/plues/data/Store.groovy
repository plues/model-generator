package de.hhu.stups.plues.data

import de.hhu.stups.plues.data.entities.AbstractUnit
import de.hhu.stups.plues.data.entities.Course
import de.hhu.stups.plues.data.entities.Group
import de.hhu.stups.plues.data.entities.Info
import de.hhu.stups.plues.data.entities.Level
import de.hhu.stups.plues.data.entities.Module
import de.hhu.stups.plues.data.entities.ModuleAbstractUnitSemester
import de.hhu.stups.plues.data.entities.ModuleAbstractUnitType
import de.hhu.stups.plues.data.entities.Session
import de.hhu.stups.plues.data.entities.Unit

// move to interface (?)
abstract class Store {
    abstract void init() throws IncompatibleSchemaError;
    abstract void init(String dbpath) throws IncompatibleSchemaError;

    abstract void close();

    abstract List<Info> getInfo();
    abstract List<AbstractUnit> getAbstractUnits();
    abstract List<Course> getCourses();
    abstract Course getCourseByKey(String key);
    abstract List<Group> getGroups();
    abstract List<Level> getLevels();
    abstract List<Module> getModules();
    abstract List<ModuleAbstractUnitSemester> getModuleAbstractUnitSemester();
    abstract List<ModuleAbstractUnitType> getModuleAbstractUnitType()
    abstract List<Session> getSessions();
    abstract List<Unit> getUnits();
    abstract AbstractUnit getAbstractUnitByID(Integer key)
    abstract Group getGroupByID(Integer integer)
    abstract String getInfoByKey(String key)

    abstract Module getModuleByID(Integer mid)
}
