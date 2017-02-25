package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "abstract_units")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "abstract_units")
@Immutable
public class AbstractUnit extends ModelEntity implements Serializable {

  private static final long serialVersionUID = 289898052527025134L;

  @Id
  @GeneratedValue
  private Integer id;

  @NaturalId
  private String key;
  private String title;

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
      && super.equals(that);
  }

  private boolean equalIdentifiers(final AbstractUnit that) {
    return Objects.equals(id, that.id) && Objects.equals(key, that.key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, key, title);
  }

  public Integer getId() {
    return id;
  }

  public void setId(final Integer id) {
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

  public Set<Unit> getUnits() {
    return units;
  }

  public void setUnits(final Set<Unit> units) {
    this.units = units;
  }

  public Set<ModuleAbstractUnitSemester> getModuleAbstractUnitSemesters() {
    return moduleAbstractUnitSemesters;
  }

  public void setModuleAbstractUnitSemesters(
      final Set<ModuleAbstractUnitSemester> moduleAbstractUnitSemesters) {
    this.moduleAbstractUnitSemesters = moduleAbstractUnitSemesters;
  }

  public Set<ModuleAbstractUnitType> getModuleAbstractUnitTypes() {
    return moduleAbstractUnitTypes;
  }

  public void setModuleAbstractUnitTypes(
      final Set<ModuleAbstractUnitType> moduleAbstractUnitTypes) {
    this.moduleAbstractUnitTypes = moduleAbstractUnitTypes;
  }

  public Set<Module> getModules() {
    return modules;
  }

  public void setModules(final Set<Module> modules) {
    this.modules = modules;
  }

}
