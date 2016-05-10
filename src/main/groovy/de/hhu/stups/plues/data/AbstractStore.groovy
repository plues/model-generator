package de.hhu.stups.plues.data

import de.hhu.stups.plues.data.entities.AbstractUnit
import de.hhu.stups.plues.data.entities.AbstractUnitUnitSemester
import de.hhu.stups.plues.data.entities.Course
import de.hhu.stups.plues.data.entities.Group
import de.hhu.stups.plues.data.entities.Info
import de.hhu.stups.plues.data.entities.Level
import de.hhu.stups.plues.data.entities.Module
import de.hhu.stups.plues.data.entities.ModuleAbstractUnitSemester
import de.hhu.stups.plues.data.entities.Session
import de.hhu.stups.plues.data.entities.Unit

abstract class AbstractStore {
    abstract def List<Info> getInfo();
    abstract def List<AbstractUnit> getAbstractUnits();
    abstract def getAbstractUnitUnitSemesterByUnitID(def unit_id);
    abstract def List<AbstractUnitUnitSemester> getAbstractUnitUnitSemester();
    abstract def List<Course> getCourses();
    abstract def Course getCourseByKey(String key);
    abstract def List<Group> getGroups();
    abstract def List<Level> getLevels();
    abstract def List<Module> getModules();
    abstract def List<ModuleAbstractUnitSemester> getModuleAbstractUnitSemester();
    abstract def List<Session> getSessions();
    abstract def List<Unit> getUnits();
    abstract def AbstractUnit getAbstractUnitByID(Integer key)
    abstract def Group getGroupByID(Integer integer)

    abstract def Module getModuleByID(Integer mid)
}
