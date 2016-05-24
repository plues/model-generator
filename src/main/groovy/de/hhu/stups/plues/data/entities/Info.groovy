package de.hhu.stups.plues.data.entities

import org.hibernate.annotations.Immutable
import org.hibernate.annotations.NaturalId

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="info")
@Immutable
class Info implements Serializable {
    @Id
    @NaturalId
    private String key
    private String value
}
