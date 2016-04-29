package de.stups.slottool.data.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type

import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@javax.persistence.Entity
@Table(name="log")
class Log implements Serializable {
    @ManyToOne
    @Id Session session

    @Id String src
    @Id String target

    @CreationTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    Date created_at

    def Log() {}
}
