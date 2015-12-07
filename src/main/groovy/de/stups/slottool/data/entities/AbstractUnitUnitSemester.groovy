package de.stups.slottool.data.entities

class AbstractUnitUnitSemester extends Entity{
    AbstractUnit abstract_unit
    Unit unit
    Integer semester

    def AbstractUnitUnitSemester(AbstractUnit abstractUnit, Unit unit, Integer s) {
        this.abstract_unit = abstractUnit
        this.unit = unit
        this.semester = s
    }
}
