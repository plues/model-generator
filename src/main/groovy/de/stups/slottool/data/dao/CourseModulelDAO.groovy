package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Course
import de.stups.slottool.data.entities.Module
import groovy.sql.Sql

class CourseModulelDAO extends AbstractDAO {
    ModuleDAO moduleDAO
    CourseDAO courseDAO

    def CourseModulelDAO(Sql sql, CourseDAO courseDAO, ModuleDAO moduleDAO) {
        super(sql)
        this.moduleDAO = moduleDAO
        this.courseDAO = courseDAO
    }

    @Override
    protected loadRow(def Object row) {
        Course course = courseDAO.getById(row['course_id'])
        Module module= moduleDAO.getById(row['module_id'])
        module.courses.add(course)
        course.modules.add(module)
    }

    @Override
    def getById(Object id) {
        throw new UnsupportedOperationException();
    }

    @Override
    String getTableName() {
        return "course_modules"
    }

    @Override
    Iterator iterator() {
        throw new UnsupportedOperationException()
    }
}
