package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Course
import de.stups.slottool.data.entities.CourseModule

class CourseModuleDAO extends AbstractDAO {
    ModuleDAO moduleDAO
    CourseDAO courseDAO
    Set<CourseModule> data

    CourseModuleDAO(def sql, CourseDAO courseDAO, ModuleDAO moduleDAO) {
        super(sql)
        this.courseDAO = courseDAO
        this.moduleDAO = moduleDAO
        this.data = new HashSet<CourseModule>()
    }

    @Override
    protected loadRow(def Object row) {
        def course = courseDAO.getById(row['course_id'])
        def module = moduleDAO.getById(row['module_id'])
        def cm = new CourseModule(course, module, row['type'], row['elective_units'])
        // TODO: add to course and module somehow (?)
        this.data.add(cm)
    }

    @Override
    def getById(Object id) {
        throw new UnsupportedOperationException()
    }

    @Override
    String getTableName() {
        return "courses_modules"
    }

    @Override
    Iterator iterator() {
        return data.iterator()
    }
}
