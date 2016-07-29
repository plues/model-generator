package de.hhu.stups.plues.data.entities

import net.sf.ehcache.util.FindBugsSuppressWarnings
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.Immutable

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name="modules_abstract_units_semesters")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY,
        region="modules_abstract_units_semesters")
@Immutable
@FindBugsSuppressWarnings(["SE_NO_SERIALVERSIONID","SE_TRANSIENT_FIELD_NOT_RESTORED", "EQ_UNUSUAL"])
class ModuleAbstractUnitSemester implements Serializable {
    @Id
    @ManyToOne
    Module module

    @Id
    @ManyToOne
    AbstractUnit abstract_unit

    @Id
    Integer semester

    public ModuleAbstractUnitSemester() {}

    @Override
    public boolean equals(Object o) {
        if (this.is(o)) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        def that = o as ModuleAbstractUnitSemester
        return (this.semester == that.semester
                && this.module == that.module
                && this.abstract_unit == that.abstract_unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.module, this.abstract_unit, this.semester);
    }
}
