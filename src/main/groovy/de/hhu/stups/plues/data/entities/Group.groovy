package de.hhu.stups.plues.data.entities

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name="groups")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY,
        region="groups")

class Group implements Serializable{
    @Id
    @GeneratedValue
    int id
    int half_semester
    @CreationTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    Date created_at
    @UpdateTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    Date updated_at

    @OneToMany(mappedBy = "group")
    Set<Session> sessions

    @ManyToOne(fetch=FetchType.EAGER)
    Unit unit

    def Group() {}

    def half_semester_word() {
        ( half_semester == 1 ) ? "first" : "second"
    }
}
