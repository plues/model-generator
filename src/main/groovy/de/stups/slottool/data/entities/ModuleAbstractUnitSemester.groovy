package de.stups.slottool.data.entities

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name="modules_abstract_units_semesters")
class ModuleAbstractUnitSemester implements Serializable {
    @Id
    @ManyToOne
    Module module

    @Id
    @ManyToOne
    AbstractUnit abstract_unit

    Integer semester

    def ModuleAbstractUnitSemester() {}
}
