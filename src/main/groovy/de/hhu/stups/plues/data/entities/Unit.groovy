package de.hhu.stups.plues.data.entities

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Immutable
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.engine.jdbc.ColumnNameCache

import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "units")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY,
        region="units")
@Immutable
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

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="unit_semester", joinColumns = @JoinColumn(name="unit_id"))
    @Column(name="semester")
    Set<Integer> semesters;

    @ManyToMany(mappedBy = "units")
    Set<AbstractUnit> abstractUnits;

    @OneToMany(mappedBy = "unit")
    Set<Group> groups

    def Unit() {}
}
