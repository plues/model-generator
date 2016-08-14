package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

  public void setId(final int id) {
    this.id = id;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(final Course course) {
    this.course = course;
  }


  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || this.getClass() != other.getClass()) {
      return false;
    }
    final ModuleCombination integers = (ModuleCombination) other;
    return this.id == integers.id
        && Arrays.equals(this.combination, integers.combination);
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
