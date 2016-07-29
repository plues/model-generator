package de.hhu.stups.plues.data.entities

import net.sf.ehcache.util.FindBugsSuppressWarnings
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
@FindBugsSuppressWarnings(["SE_TRANSIENT_FIELD_NOT_RESTORED", "EQ_UNUSUAL",
                           "SE_NO_SERIALVERSIONID", "EI_EXPOSE_REP2", "EI_EXPOSE_REP"])
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

    public Log() {}

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Log log = (Log) o

        if (created_at != log.created_at) return false
        if (session != log.session) return false
        if (src != log.src) return false
        if (target != log.target) return false

        return true
    }

    int hashCode() {
        int result
        result = (session != null ? session.hashCode() : 0)
        result = 31 * result + (src != null ? src.hashCode() : 0)
        result = 31 * result + (target != null ? target.hashCode() : 0)
        result = 31 * result + (created_at != null ? created_at.hashCode() : 0)
        return result
    }
}
