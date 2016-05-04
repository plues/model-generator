package de.stups.slottool.data

import de.stups.slottool.data.entities.AbstractUnit
import de.stups.slottool.data.entities.AbstractUnitUnitSemester
import de.stups.slottool.data.entities.Course
import de.stups.slottool.data.entities.Group
import de.stups.slottool.data.entities.Info
import de.stups.slottool.data.entities.Level
import de.stups.slottool.data.entities.Module
import de.stups.slottool.data.entities.ModuleAbstractUnitSemester
import de.stups.slottool.data.entities.Session
import de.stups.slottool.data.entities.Unit

abstract class AbstractStore {
    abstract def List<Info> getInfo();
    abstract def List<AbstractUnit> getAbstractUnits();
    abstract def getAbstractUnitUnitSemesterByUnitID(def unit_id);
    abstract def List<AbstractUnitUnitSemester> getAbstractUnitUnitSemester();
    abstract def List<Course> getCourses();
    abstract def Course getCourseByKey(String key);
    abstract def List<Group> getGroups();
    abstract def List<Group> getGroupsByIDs(Collection<Integer> entries);
    abstract def List<Level> getLevels();
    abstract def List<Module> getModules();
    abstract def List<ModuleAbstractUnitSemester> getModuleAbstractUnitSemester();
    abstract def List<Session> getSessions();
    abstract def List<Unit> getUnits();
}
