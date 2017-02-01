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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "abstract_units")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "abstract_units")
@Immutable
public class AbstractUnit implements Serializable {

  private static final long serialVersionUID = 6530093545778592625L;

  @Id
  @GeneratedValue
  private Integer id;

  @NaturalId
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

  @ManyToMany
  @JoinTable(name = "unit_abstract_unit",
      joinColumns = @JoinColumn(name = "abstract_unit_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "unit_id", referencedColumnName = "id"))
  private Set<Unit> units;

  @OneToMany(mappedBy = "abstractUnit")
  private Set<ModuleAbstractUnitSemester> moduleAbstractUnitSemesters;

  @OneToMany(mappedBy = "abstractUnit")
  private Set<ModuleAbstractUnitType> moduleAbstractUnitTypes;

  @ManyToMany
  @JoinTable(name = "modules_abstract_units_types",
      joinColumns = @JoinColumn(name = "abstract_unit_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "module_id", referencedColumnName = "id"))
  @Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "modules")
  private Set<Module> modules;

  public AbstractUnit() {
    // Default constructor is required by hibernate
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    final AbstractUnit that = (AbstractUnit) other;
    return equalIdentifiers(that)
      && Objects.equals(title, that.title)
      && equalTimestamps(that);
  }

  private boolean equalIdentifiers(final AbstractUnit that) {
    return Objects.equals(id, that.id) && Objects.equals(key, that.key);
  }

  private boolean equalTimestamps(final AbstractUnit that) {
    return Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, key, title, createdAt, updatedAt);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getCreatedAt() {
    return (Date) createdAt.clone();
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = (Date) createdAt.clone();
  }

  public Date getUpdatedAt() {
    return (Date) updatedAt.clone();
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = (Date) updatedAt.clone();
  }

  public Set<Unit> getUnits() {
    return units;
  }

  public void setUnits(Set<Unit> units) {
    this.units = units;
  }

  public Set<ModuleAbstractUnitSemester> getModuleAbstractUnitSemesters() {
    return moduleAbstractUnitSemesters;
  }

  public void setModuleAbstractUnitSemesters(
      Set<ModuleAbstractUnitSemester> moduleAbstractUnitSemesters) {
    this.moduleAbstractUnitSemesters = moduleAbstractUnitSemesters;
  }

  public Set<ModuleAbstractUnitType> getModuleAbstractUnitTypes() {
    return moduleAbstractUnitTypes;
  }

  public void setModuleAbstractUnitTypes(Set<ModuleAbstractUnitType> moduleAbstractUnitTypes) {
    this.moduleAbstractUnitTypes = moduleAbstractUnitTypes;
  }

  public Set<Module> getModules() {
    return modules;
  }

  public void setModules(Set<Module> modules) {
    this.modules = modules;
  }

}
