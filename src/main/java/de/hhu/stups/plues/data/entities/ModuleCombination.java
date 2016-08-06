package de.hhu.stups.plues.data.entities;

import net.sf.ehcache.util.FindBugsSuppressWarnings;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "course_modules_combinations")
@Immutable
@FindBugsSuppressWarnings({"SE_NO_SERIALVERSIONID",
        "SE_TRANSIENT_FIELD_NOT_RESTORED", "EQ_UNUSUAL"})
public class ModuleCombination implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @Column(name="combination_id")
    private int combinationId;

    @Column(name="module_id")
    private int moduleId;

    @ManyToOne
    private Course course;

    @Override
    public boolean equals(Object o) {
        if(DefaultGroovyMethods.is(this, o)) {
            return true;
        }

        if(o == null || !getClass().equals(o.getClass())) {
            return false;
        }

        ModuleCombination that = DefaultGroovyMethods.asType(o, ModuleCombination.class);
        return (combinationId == that.combinationId && moduleId == that.moduleId);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.combinationId, this.course, this.moduleId);
    }

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


}
