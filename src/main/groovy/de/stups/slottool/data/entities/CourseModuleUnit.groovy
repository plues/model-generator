package de.stups.slottool.data.entities

class CourseModuleUnit {
    Course course
    Module module
    Unit unit
    String type
    int semester

    CourseModuleUnit(Course course, Module module, Unit unit, String type, int semester) {
        this.course = course
        this.module = module
        this.unit = unit
        this.type = type
        this.semester = semester

    }
}
