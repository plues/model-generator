package de.stups.slottool.data.entities

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name="unit_abstract_unit_semester")
class AbstractUnitUnitSemester implements Serializable {
    @Id
    @ManyToOne
    AbstractUnit abstract_unit
    @Id
    @ManyToOne
    Unit unit

    Integer semester

    def AbstractUnitUnitSemester() {}

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        def that = o as AbstractUnitUnitSemester
        return (semester == that.semester
                && Objects.equals( abstract_unit, that.abstract_unit )
                && Objects.equals( unit, that.unit ));
    }

    @Override
    public int hashCode() {
        return Objects.hash(semester, abstract_unit, unit);
    }
}
