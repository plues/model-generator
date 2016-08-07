package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "modules_abstract_units_semesters")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "modules_abstract_units_semesters")
@Immutable
public class ModuleAbstractUnitSemester implements Serializable {

    private static final long serialVersionUID = 9021333343897176890L;
    @Id
    @ManyToOne
    private Module module;

    @Id
    @ManyToOne
    @JoinColumn(name = "abstract_unit_id")
    private AbstractUnit abstractUnit;

    @Id
    private Integer semester;

    public ModuleAbstractUnitSemester() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleAbstractUnitSemester that = (ModuleAbstractUnitSemester) o;
        return Objects.equals(module, that.module) &&
                Objects.equals(abstractUnit, that.abstractUnit) &&
                Objects.equals(semester, that.semester);
    }

    @Override
    public int hashCode() {
        return Objects.hash(module, abstractUnit, semester);
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public AbstractUnit getAbstractUnit() {
        return abstractUnit;
    }

    public void setAbstractUnit(AbstractUnit abstractUnit) {
        this.abstractUnit = abstractUnit;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }
}
