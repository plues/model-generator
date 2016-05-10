package de.hhu.stups.plues.data.entities

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.CreationTimestamp
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
class AbstractUnit implements Serializable{
    @Id
    @GeneratedValue
    Integer id
    @NaturalId
    String key
    String title
    Character type
    @CreationTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    Date created_at
    @UpdateTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    Date updated_at

    @OneToMany(mappedBy="abstract_unit")
    Set<AbstractUnitUnitSemester> abstract_unit_unit_semester

    @OneToMany(mappedBy="abstract_unit")
    Set<ModuleAbstractUnitSemester> module_abstract_unit_semester

    @ManyToMany()
    @JoinTable(name="modules_abstract_units_semesters",
        joinColumns=@JoinColumn(name="abstract_unit_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="module_id", referencedColumnName="id"))
    @Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
    Set<Module> modules

    def AbstractUnit() {}

}
