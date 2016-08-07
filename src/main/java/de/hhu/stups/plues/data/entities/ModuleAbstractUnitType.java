package de.hhu.stups.plues.data.entities;

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

@Entity
@Table(name = "modules_abstract_units_types")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "modules_abstract_units_types")
@Immutable
public class ModuleAbstractUnitType implements Serializable {

    @Id
    @ManyToOne
    private Module module;

    @Id
    @ManyToOne
    @JoinColumn(name = "abstract_unit_id")
    private AbstractUnit abstractUnit;

    @Id
    private Character type;

    public boolean equals(Object o) {
        if(DefaultGroovyMethods.is(this, o)) return true;
        if(!getClass().equals(o.getClass())) return false;

        ModuleAbstractUnitType that = (ModuleAbstractUnitType) o;

        if(!type.equals(that.type)) return false;
        if(!abstractUnit.equals(that.abstractUnit)) return false;
        if(!module.equals(that.module)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = ((int) ((module != null ? module.hashCode() : 0)));
        result = ((int) (31 * result + (abstractUnit != null ? abstractUnit.hashCode() : 0)));
        result = 31 * result + (int) type;
        return result;
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

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }
}
