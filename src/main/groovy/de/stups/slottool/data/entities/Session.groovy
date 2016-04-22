package de.stups.slottool.data.entities

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
    String time
    Integer rhythm
    Integer duration
    Date created_at
    Date updated_at

    @ManyToOne
    Group group

    def Session() {}
}
