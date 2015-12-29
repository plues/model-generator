package de.stups.slottool.data.dao

import groovy.sql.Sql

class CourseModuleCombinationsDAO extends AbstractDAO {
    Map combinations
    ModuleDAO moduleDAO
    CourseDAO courseDAO

    CourseModuleCombinationsDAO(Sql sql, CourseDAO courseDAO, ModuleDAO moduleDAO) {
        super(sql)
        this.combinations = [:]
        this.courseDAO = courseDAO
        this.moduleDAO = moduleDAO
    }

    @Override
    String getOrderByClause() {
        return "course_id, combination_id, module_id"
    }

    @Override
    protected loadRow(def row) {
        def course = this.courseDAO.getById(row['course_id'])
        def module = this.moduleDAO.getById(row['module_id'])
        def combination = row['combination_id']
        if(!combinations[course]) {
            combinations[course] = [:]
        }
        if(!combinations[course][combination]) {
            combinations[course][combination] = []
        }
        this.combinations[course][combination] << module
    }

    @java.lang.Override
    def getById(def id) {
        throw new UnsupportedOperationException();
    }

    @java.lang.Override
    String getTableName() {
        return "course_modules_combinations"
    }

    @Override
    Iterator iterator() {
        return this.combinations.iterator()
    }
}
