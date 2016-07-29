package de.hhu.stups.plues.data.entities

import net.sf.ehcache.util.FindBugsSuppressWarnings
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Immutable
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@javax.persistence.Entity
@Table(name="abstract_units")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY,
        region="abstract_units")
@Immutable
@FindBugsSuppressWarnings(["SE_NO_SERIALVERSIONID","SE_TRANSIENT_FIELD_NOT_RESTORED", "EI_EXPOSE_REP", "EI_EXPOSE_REP2"])
class AbstractUnit implements Serializable {

    @Id
    @GeneratedValue
    Integer id

    @NaturalId
    String key

    String title

    @SuppressWarnings("GroovyUnusedDeclaration")
    @CreationTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    Date created_at

    @SuppressWarnings("GroovyUnusedDeclaration")
    @UpdateTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    Date updated_at

    @SuppressWarnings("GroovyUnusedDeclaration")
    @ManyToMany()
    @JoinTable(name="unit_abstract_unit",
            joinColumns=@JoinColumn(name="abstract_unit_id", referencedColumnName = "id"),
            inverseJoinColumns=@JoinColumn(name="unit_id", referencedColumnName = "id"))
    Set<Unit> units;

    @SuppressWarnings("GroovyUnusedDeclaration")
    @OneToMany(mappedBy="abstract_unit")
    Set<ModuleAbstractUnitSemester> module_abstract_unit_semester

    @SuppressWarnings("GroovyUnusedDeclaration")
    @OneToMany(mappedBy="abstract_unit")
    Set<ModuleAbstractUnitType> module_abstract_unit_type

    @SuppressWarnings("GroovyUnusedDeclaration")
    @ManyToMany()
    @JoinTable(name="modules_abstract_units_types",
        joinColumns=@JoinColumn(name="abstract_unit_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="module_id", referencedColumnName="id"))
    @Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
    Set<Module> modules

    public AbstractUnit() {}

}
