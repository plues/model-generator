package de.stups.slottool.data.dao

import de.stups.slottool.data.entities.Course

class CourseDAO extends AbstractDAO {
    Map<Integer, Course> courses
    def String getTableName() {
        "courses"
    }

    def CourseDAO(def sql) {
        super(sql)
        this.courses = new HashMap<Integer, Course>()
    }

    @Override
    def protected loadRow(def row) {
        this.courses.put(row['id'], new Course(
                row['id'], row['name'], row['short_name'], row['degree'], row['po'],
                Date.parse(DATE_FORMAT, row['created_at']), Date.parse(DATE_FORMAT, row['updated_at'])))
    }

    @Override
    def getById(def id) {
        return courses.get(id)
    }

    @Override
    Iterator iterator() {
        return this.courses.values().iterator()
    }
}
