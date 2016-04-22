package de.stups.slottool.data.entities

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

    Date created_at

    def Log() {}
}
