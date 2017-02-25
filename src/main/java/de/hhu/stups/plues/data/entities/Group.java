package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "groups")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "groups")
@Immutable
public class Group extends ModelEntity implements Serializable {

  private static final long serialVersionUID = 4765838728047788944L;
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "half_semester")
  private int halfSemester;

  @OneToMany(mappedBy = "group")
  private Set<Session> sessions;

  @ManyToOne(fetch = FetchType.EAGER)
  private Unit unit;


  public Group() {
    // Default constructor is required by hibernate
  }

  public String getHalfSemesterWord() {
    return (halfSemester == 1) ? "first" : "second";
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public int getHalfSemester() {
    return halfSemester;
  }

  public void setHalfSemester(final int halfSemester) {
    this.halfSemester = halfSemester;
  }

  public Set<Session> getSessions() {
    return sessions;
  }

  public void setSessions(final Set<Session> sessions) {
    this.sessions = sessions;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(final Unit unit) {
    this.unit = unit;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    final Group group = (Group) other;
    return id == group.id
        && halfSemester == group.halfSemester
        && super.equals(group);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, halfSemester, super.hashCode());
  }
}
