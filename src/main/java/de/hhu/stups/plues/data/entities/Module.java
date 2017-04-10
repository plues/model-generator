package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "modules")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "modules")
@Immutable
public class Module extends ModelEntity implements Serializable {

  private static final long serialVersionUID = 7922130660874935173L;

  @Id
  @GeneratedValue
  private int id;

  @NaturalId
  private String key;

  private String title;

  private Integer pordnr;
  @Column(name = "elective_units")
  private Integer electiveUnits;

  @ManyToMany(mappedBy = "modules", fetch = FetchType.LAZY)
  private Set<AbstractUnit> abstractUnits;

  @ManyToMany(mappedBy = "modules", fetch = FetchType.LAZY)
  private Set<Course> courses;

  @OneToMany(mappedBy = "module")
  private Set<ModuleLevel> moduleLevels;

  @OneToMany(mappedBy = "module")
  private Set<ModuleAbstractUnitSemester> moduleAbstractUnitSemesters;

  @OneToMany(mappedBy = "module")
  private Set<ModuleAbstractUnitType> moduleAbstractUnitTypes;

  public Module() {
    // Default constructor is required by hibernate
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public Integer getPordnr() {
    return pordnr;
  }

  public void setPordnr(final Integer pordnr) {
    this.pordnr = pordnr;
  }

  public Integer getElectiveUnits() {
    return electiveUnits;
  }

  public void setElectiveUnits(final Integer electiveUnits) {
    this.electiveUnits = electiveUnits;
  }

  public Set<ModuleLevel> getModuleLevels() {
    return this.moduleLevels;
  }

  public Set<AbstractUnit> getAbstractUnits() {
    return abstractUnits;
  }

  public void setAbstractUnits(final Set<AbstractUnit> abstractUnits) {
    this.abstractUnits = abstractUnits;
  }

  public Set<ModuleAbstractUnitSemester> getModuleAbstractUnitSemesters() {
    return moduleAbstractUnitSemesters;
  }

  public void setModuleAbstractUnitSemesters(
      final Set<ModuleAbstractUnitSemester> moduleAbstractUnitSemesters) {
    this.moduleAbstractUnitSemesters = moduleAbstractUnitSemesters;
  }

  /**
   * Returns the set of semesters in which the abstract unit au is associated to this module.
   *
   * @param au AbstractUnit
   * @return Set of semester numbers.
   */
  public Set<Integer> getSemestersForAbstractUnit(final AbstractUnit au) {
    return this.moduleAbstractUnitSemesters.stream()
      .filter(maus -> maus.getAbstractUnit().equals(au))
      .map(ModuleAbstractUnitSemester::getSemester)
      .collect(Collectors.toSet());
  }

  public Set<ModuleAbstractUnitType> getModuleAbstractUnitTypes() {
    return moduleAbstractUnitTypes;
  }

  public void setModuleAbstractUnitTypes(
      final Set<ModuleAbstractUnitType> moduleAbstractUnitTypes) {
    this.moduleAbstractUnitTypes = moduleAbstractUnitTypes;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    final Module module = (Module) other;
    return id == module.id
      && Objects.equals(key, module.key)
      && Objects.equals(title, module.title)
      && Objects.equals(pordnr, module.pordnr)
      && Objects.equals(electiveUnits, module.electiveUnits)
      && super.equals(other);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, key, title, pordnr, electiveUnits, super.hashCode());
  }

  public Set<Course> getCourses() {
    return courses;
  }

}
