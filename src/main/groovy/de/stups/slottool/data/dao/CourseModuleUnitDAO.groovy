package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.CourseModuleUnit

class CourseModuleUnitDAO extends AbstractDAO{
    CourseDAO courseDAO
    ModuleDAO moduleDAO
    UnitDAO unitDAO
    HashSet<CourseModuleUnit> data

    CourseModuleUnitDAO(def sql, CourseDAO courseDAO, ModuleDAO moduleDAO, UnitDAO unitDAO) {
        super(sql)
        this.courseDAO = courseDAO
        this.moduleDAO = moduleDAO
        this.unitDAO = unitDAO
        this.data = new HashSet<CourseModuleUnit>();

    }

    @Override
    protected loadRow(def Object row) {
        def unit = unitDAO.getById(row['unit_id'])
        def course = courseDAO.getById(row['course_id'])
        def module = moduleDAO.getById(row['module_id'])

        assert unit != null
        assert course != null
        assert module != null

        def cmu = new CourseModuleUnit(course, module, unit, row['type'], row['semester'])
        this.data.add(cmu)
    }

    @Override
    def getById(Object id) {
        raise new UnsupportedOperationException();
    }

    @Override
    String getTableName() {
        return "courses_modules_units"
    }

    @Override
    Iterator iterator() {
        return data.iterator()
    }
}
