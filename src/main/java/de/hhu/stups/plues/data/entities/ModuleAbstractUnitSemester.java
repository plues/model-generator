package de.hhu.stups.plues.data.entities;

import net.sf.ehcache.util.FindBugsSuppressWarnings;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "modules_abstract_units_semesters")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "modules_abstract_units_semesters")
@Immutable
@FindBugsSuppressWarnings({"SE_NO_SERIALVERSIONID", "SE_TRANSIENT_FIELD_NOT_RESTORED", "EQ_UNUSUAL"})
public class ModuleAbstractUnitSemester implements Serializable {

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
        if(DefaultGroovyMethods.is(this, o)) {
            return true;
        }

        if(o == null || !getClass().equals(o.getClass())) {
            return false;
        }

        ModuleAbstractUnitSemester that = DefaultGroovyMethods.asType(o, ModuleAbstractUnitSemester.class);
        return (this.semester == that.semester && this.module.equals(that.module) && this.abstractUnit.equals(that.abstractUnit));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.module, this.abstractUnit, this.semester);
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
