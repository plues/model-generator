package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Objects;

@Entity
@Table(name = "course_modules_combinations")
@Immutable
public class ModuleCombination implements Serializable, Iterable<Integer> {
    private static final long serialVersionUID = 768471461766386796L;
    @Id
    @GeneratedValue
    private int id;

    @Column(columnDefinition = "BLOB NOT NULL")
    @SuppressWarnings("unused")
    private byte[] combination;

    @ManyToOne
    private Course course;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    @Override
    public boolean equals(final Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ModuleCombination integers = (ModuleCombination) o;
        return this.id == integers.id &&
                Arrays.equals(this.combination, integers.combination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.combination);
    }

    @Override
    public Iterator<Integer> iterator() {
        return BitSet.valueOf(this.combination).stream().iterator();
    }
}
