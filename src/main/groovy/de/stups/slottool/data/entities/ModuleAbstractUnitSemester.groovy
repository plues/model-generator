package de.stups.slottool.data.entities

class ModuleAbstractUnitSemester {
    Module module
    AbstractUnit abstract_unit
    Integer semeter

    def ModuleAbstractUnitSemester(Module module, AbstractUnit au, Integer semester) {
        this.module = module
        this.abstract_unit = au
        this.semeter = semester
    }
}
