package de.stups.slottool.data.entities

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "units")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY,
        region="units")
class Unit implements Serializable {

    @Id
    @GeneratedValue
    int id

    @NaturalId
    @Column(name = "unit_key")
    String key

    String title
    @CreationTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    Date created_at
    @UpdateTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
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
