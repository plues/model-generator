package de.hhu.stups.plues.data.entities

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type

import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@javax.persistence.Entity
@Table(name="log")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE,
        region="log")
class Log implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    @Id
    Session session

    @Id
    String src
    @Id
    String target

    @CreationTimestamp
    @Type(type = "org.hibernate.usertype.SQLiteDateTimeType")
    Date created_at

    def Log() {}

    @Override
    public boolean equals(Object o) {
        if (this.is(o)) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        def that = o as Log
        return (this.session == that.session
                && this.src == that.src
                && this.target == that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.src, this.target, this.session)

    }
}
