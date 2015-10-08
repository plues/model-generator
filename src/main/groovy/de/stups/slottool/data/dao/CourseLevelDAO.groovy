package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Course
import de.stups.slottool.data.entities.Level
import groovy.sql.Sql

class CourseLevelDAO extends AbstractDAO {
    LevelDAO levelDAO
    CourseDAO courseDAO

    def CourseLevelDAO(Sql sql, CourseDAO courseDAO, LevelDAO levelDAO) {
        super(sql)
        this.levelDAO = levelDAO
        this.courseDAO = courseDAO
    }

    @Override
    protected loadRow(def Object row) {
        Course course = courseDAO.getById(row['course_id'])
        Level level= levelDAO.getById(row['level_id'])
        course.levels.add(level)
        level.course = course
    }

    @Override
    def getById(Object id) {
        throw new UnsupportedOperationException();
    }

    @Override
    String getTableName() {
        return "course_levels"
    }

    @Override
    Iterator iterator() {
        throw new UnsupportedOperationException()
    }
}
