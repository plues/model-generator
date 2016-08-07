package de.hhu.stups.plues.data;

import de.hhu.stups.plues.data.entities.AbstractUnit;
import de.hhu.stups.plues.data.entities.Course;
import de.hhu.stups.plues.data.entities.Group;
import de.hhu.stups.plues.data.entities.Level;
import de.hhu.stups.plues.data.entities.Module;
import de.hhu.stups.plues.data.entities.ModuleAbstractUnitSemester;
import de.hhu.stups.plues.data.entities.ModuleAbstractUnitType;
import de.hhu.stups.plues.data.entities.Session;
import de.hhu.stups.plues.data.entities.Unit;

import java.util.List;

public abstract class Store {
    public abstract void init() throws IncompatibleSchemaError, StoreExeception;

    public abstract void init(String dbpath) throws IncompatibleSchemaError, StoreExeception;

    public abstract void close();

    public abstract void clear();

    public abstract List getInfo();

    public abstract List<AbstractUnit> getAbstractUnits();

    public abstract List<Course> getCourses();

    public abstract Course getCourseByKey(String key);

    public abstract List<Group> getGroups();

    public abstract List<Level> getLevels();

    public abstract List<Module> getModules();

    public abstract List<ModuleAbstractUnitSemester> getModuleAbstractUnitSemester();

    public abstract List<ModuleAbstractUnitType> getModuleAbstractUnitType();

    public abstract List<Session> getSessions();

    public abstract List<Unit> getUnits();

    public abstract AbstractUnit getAbstractUnitByID(Integer key);

    public abstract Group getGroupByID(Integer integer);

    public abstract String getInfoByKey(String key);

    public abstract Module getModuleByID(Integer mid);

}
