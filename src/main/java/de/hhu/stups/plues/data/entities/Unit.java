package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "units")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "units")
@Immutable
public class Unit implements Serializable {

  private static final long serialVersionUID = 8613291209531976009L;
  @Id
  @GeneratedValue
  private int id;

  @NaturalId
  @Column(name = "unit_key")
  private String key;

  private String title;

  @CreationTimestamp
  @Type(type = "org.hibernate.usertype.SqliteDateTimeType")
  @Column(name = "created_at")
  private Date createdAt;

  @UpdateTimestamp
  @Type(type = "org.hibernate.usertype.SqliteDateTimeType")
  @Column(name = "updated_at")
  private Date updatedAt;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "unit_semester",
      joinColumns = @JoinColumn(name = "unit_id"))
  @Column(name = "semester")
  private Set<Integer> semesters;

  @ManyToMany(mappedBy = "units")
  private Set<AbstractUnit> abstractUnits;

  @OneToMany(mappedBy = "unit")
  private Set<Group> groups;

  public Unit() {
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
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

  public Set<Integer> getSemesters() {
    return semesters;
  }

  public void setSemesters(final Set<Integer> semesters) {
    this.semesters = semesters;
  }

  public Set<AbstractUnit> getAbstractUnits() {
    return abstractUnits;
  }

  public void setAbstractUnits(final Set<AbstractUnit> abstractUnits) {
    this.abstractUnits = abstractUnits;
  }

  public Set<Group> getGroups() {
    return groups;
  }

  public void setGroups(final Set<Group> groups) {
    this.groups = groups;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    final Unit unit = (Unit) other;
    return id == unit.id
        && Objects.equals(key, unit.key)
        && Objects.equals(title, unit.title)
        && Objects.equals(createdAt, unit.createdAt)
        && Objects.equals(updatedAt, unit.updatedAt)
        && Objects.equals(semesters, unit.semesters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, key, title, createdAt, updatedAt, semesters);
  }
}
