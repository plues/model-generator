package de.stups.slottool.data.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.Entity
import javax.persistence.GeneratedValue;
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table;

@Entity
@Table(name="sessions")
class Session {
    @Id
    @GeneratedValue
    int id
    String day
    Integer time
    Integer rhythm
    Integer duration
    @CreationTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    Date created_at
    @UpdateTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    Date updated_at

    @ManyToOne
    Group group

    def Session() {}
}
