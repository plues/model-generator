package de.stups.slottool.data.entities

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue;
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table;

@Entity
@Table(name="sessions")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,
        region="session")
class Session implements Serializable {
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

    @ManyToOne(fetch=FetchType.EAGER)
    Group group

    def Session() {}
}
