package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
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

@Entity
@Table(name = "groups")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "groups")
@Immutable
public class Group implements Serializable {

  private static final long serialVersionUID = -2048455700838010908L;
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "half_semester")
  private int halfSemester;

  @CreationTimestamp
  @Type(type = "org.hibernate.usertype.SqliteDateTimeType")
  @Column(name = "created_at")
  private Date createdAt;

  @UpdateTimestamp
  @Type(type = "org.hibernate.usertype.SqliteDateTimeType")
  @Column(name = "updated_at")
  private Date updatedAt;

  @OneToMany(mappedBy = "group")
  private Set<Session> sessions;

  @ManyToOne(fetch = FetchType.EAGER)
  private Unit unit;


  public Group() {
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

  public Date getCreatedAt() {
    return (Date) createdAt.clone();
  }

  public void setCreatedAt(final Date createdAt) {
    this.createdAt = (Date) createdAt.clone();
  }

  public Date getUpdatedAt() {
    return (Date) updatedAt.clone();
  }

  public void setUpdatedAt(final Date updatedAt) {
    this.updatedAt = (Date) updatedAt.clone();
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
        && Objects.equals(createdAt, group.createdAt)
        && Objects.equals(updatedAt, group.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, halfSemester, createdAt, updatedAt);
  }
}
