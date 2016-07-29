package de.hhu.stups.plues.data.entities

import net.sf.ehcache.util.FindBugsSuppressWarnings
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
@FindBugsSuppressWarnings(["SE_NO_SERIALVERSIONID","SE_TRANSIENT_FIELD_NOT_RESTORED", "EI_EXPOSE_REP", "EI_EXPOSE_REP2"])
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

    public Session() {}
}
