package de.stups.slottool.data.entities

import org.hibernate.annotations.NaturalId

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "units")
class Unit {

    @Id
    @GeneratedValue
    int id

    @NaturalId
    @Column(name = "unit_key")
    String key

    String title
    Date created_at
    Date updated_at

    @OneToMany(mappedBy = "unit")
    Set<AbstractUnitUnitSemester> abstract_unit_unit_semester

    @OneToMany(mappedBy = "unit")
    Set<Group> groups

    def Unit() {}

    @SuppressWarnings("GroovyUnusedDeclaration")
    def getAbstractUnits() {
        // cache result
        new HashSet<>(abstract_unit_unit_semester.collect { it.abstract_unit })
    }
}
