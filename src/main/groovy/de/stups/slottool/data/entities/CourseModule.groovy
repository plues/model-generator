package de.stups.slottool.data.entities

class CourseModule {
    Course course
    Module module
    String type
    int elective_units

    CourseModule(Course course, Module module, String type, int elective_units) {
        this.course = course
        this.module = module
        this.type = type
        this.elective_units = elective_units
    }
}
