package de.stups.slottool.data.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany
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

    String name
    String title
    Integer pordnr
    Boolean mandatory
    Integer elective_units
    Integer credit_points
    @UpdateTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    Date updated_at
    @CreationTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
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
