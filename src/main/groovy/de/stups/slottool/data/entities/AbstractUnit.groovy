package de.stups.slottool.data.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.loader.plan.spi.Join

import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@javax.persistence.Entity
@Table(name="abstract_units")
class AbstractUnit {
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
    Set<Module> modules

    def AbstractUnit() {}

}
