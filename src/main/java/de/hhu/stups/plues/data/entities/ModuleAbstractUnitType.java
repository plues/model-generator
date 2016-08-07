package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "modules_abstract_units_types")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "modules_abstract_units_types")
@Immutable
public class ModuleAbstractUnitType implements Serializable {

    private static final long serialVersionUID = 114371757676964184L;
    @Id
    @ManyToOne
    private Module module;

    @Id
    @ManyToOne
    @JoinColumn(name = "abstract_unit_id")
    private AbstractUnit abstractUnit;

    @Id
    private Character type;

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

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleAbstractUnitType that = (ModuleAbstractUnitType) o;
        return Objects.equals(module, that.module) &&
                Objects.equals(abstractUnit.getId(), that.abstractUnit.getId()) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(module, abstractUnit, type);
    }
}
