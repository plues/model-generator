package de.stups.slottool.data.entities

import org.hibernate.annotations.NaturalId
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
    Date created_at
    Date updated_at

    @OneToMany(mappedBy="abstract_unit", fetch=FetchType.LAZY)
    Set<AbstractUnitUnitSemester> abstract_unit_unit_semester

    @OneToMany(mappedBy="abstract_unit", fetch=FetchType.LAZY)
    Set<ModuleAbstractUnitSemester> module_abstract_unit_semester

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="modules_abstract_units_semesters",
        joinColumns=@JoinColumn(name="abstract_unit_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="module_id", referencedColumnName="id"))
    Set<Module> modules

    def AbstractUnit() {}

}
