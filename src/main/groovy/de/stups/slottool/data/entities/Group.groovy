package de.stups.slottool.data.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name="groups")
class Group {
    @Id
    @GeneratedValue
    int id
    int half_semester
    Date created_at
    Date updated_at

    @OneToMany(mappedBy = "group")
    Set<Session> sessions

    @ManyToOne
    Unit unit

    def Group() {}

    def half_semester_word() {
        ( half_semester == 1 ) ? "first" : "second"
    }
}
