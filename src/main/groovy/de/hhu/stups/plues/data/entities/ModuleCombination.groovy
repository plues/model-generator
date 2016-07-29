package de.hhu.stups.plues.data.entities

import net.sf.ehcache.util.FindBugsSuppressWarnings
import org.hibernate.annotations.Immutable

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table


@Entity
@Table(name="course_modules_combinations")
@Immutable
@FindBugsSuppressWarnings(["SE_NO_SERIALVERSIONID","SE_TRANSIENT_FIELD_NOT_RESTORED", "EQ_UNUSUAL"])
class ModuleCombination implements Serializable {
    @Id
    @GeneratedValue
    int id

    int combination_id
    int module_id

    @ManyToOne
    Course course;

    @Override
    public boolean equals(Object o) {
        if (this.is(o)) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        def that = o as ModuleCombination
        return (combination_id == that.combination_id
                && module_id == that.module_id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.combination_id, this.course, this.module_id)
    }
}
