package de.hhu.stups.plues.data.entities

import net.sf.ehcache.util.FindBugsSuppressWarnings
import org.hibernate.annotations.Immutable
import org.hibernate.annotations.NaturalId

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="info")
@Immutable
@FindBugsSuppressWarnings(["SE_NO_SERIALVERSIONID","SE_TRANSIENT_FIELD_NOT_RESTORED"])
class Info implements Serializable {
    @Id
    @NaturalId
    private String key
    private String value
}
