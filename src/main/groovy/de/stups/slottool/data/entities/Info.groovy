package de.stups.slottool.data.entities

import org.hibernate.annotations.NaturalId

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="info")
class Info implements Serializable {
    @Id
    @NaturalId
    private String key
    private String value
}
