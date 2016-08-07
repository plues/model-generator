package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "course_modules_combinations")
@Immutable
public class ModuleCombination implements Serializable {
    private static final long serialVersionUID = 571230335150242610L;
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "combination_id")
    private int combinationId;

    @Column(name = "module_id")
    private int moduleId;

    @ManyToOne
    private Course course;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCombinationId() {
        return combinationId;
    }

    public void setCombinationId(int combinationId) {
        this.combinationId = combinationId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleCombination that = (ModuleCombination) o;
        return id == that.id &&
                combinationId == that.combinationId &&
                moduleId == that.moduleId &&
                Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, combinationId, moduleId, course);
    }
}
