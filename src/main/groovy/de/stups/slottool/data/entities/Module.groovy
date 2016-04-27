package de.stups.slottool.data.entities

import org.hibernate.annotations.NaturalId

import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Entity

@Entity
@Table(name="modules")
class Module {
    @Id
    @GeneratedValue
    int id
    @NaturalId
    private String key

    private String name
    private String title
    private Integer pordnr
    private Boolean mandatory
    private Integer elective_units
    private Integer credit_points
    Date updated_at
    Date created_at

    @ManyToMany(mappedBy = "modules")
    Set<Course> courses

    @ManyToMany(mappedBy="modules")
    Set<AbstractUnit> abstract_units

    @OneToMany(mappedBy="module")
    Set<ModuleAbstractUnitSemester> module_abstract_units_semester

    def Module() {}

    def getCredit_points() {
        if(credit_points == null ) {
            return -1
        }
        return credit_points
    }
}
