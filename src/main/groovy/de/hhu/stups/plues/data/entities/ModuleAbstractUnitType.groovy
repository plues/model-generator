package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="modules_abstract_units_types")
@Cache(usage= CacheConcurrencyStrategy.READ_ONLY,
        region="modules_abstract_units_types")
@Immutable
public class ModuleAbstractUnitType implements Serializable {
    @Id
    @ManyToOne
    Module module

    @Id
    @ManyToOne
    AbstractUnit abstract_unit

    @Id
    Character type

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        ModuleAbstractUnitType that = (ModuleAbstractUnitType) o

        if (type != that.type) return false
        if (abstract_unit != that.abstract_unit) return false
        if (module != that.module) return false

        return true
    }

    int hashCode() {
        int result
        result = (module != null ? module.hashCode() : 0)
        result = 31 * result + (abstract_unit != null ? abstract_unit.hashCode() : 0)
        result = 31 * result + (int) type
        return result
    }
}
