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
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "modules")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "modules")
@Immutable
public class Module implements Serializable {

  private static final long serialVersionUID = -9153665188193235995L;

  @Id
  @GeneratedValue
  private int id;

  @NaturalId
  private String key;

  private String name;

  private String title;

  private Integer pordnr;

  private Boolean mandatory;

  @Column(name = "elective_units")
  private Integer electiveUnits;

  @Column(name = "credit_points")
  private Integer creditPoints;

  @UpdateTimestamp
  @Type(type = "org.hibernate.usertype.SqliteDateTimeType")
  @Column(name = "updated_at")
  private Date updatedAt;

  @CreationTimestamp
  @Type(type = "org.hibernate.usertype.SqliteDateTimeType")
  @Column(name = "created_at")
  private Date createdAt;

  @ManyToMany(mappedBy = "modules", fetch = FetchType.LAZY)
  private Set<Course> courses;

  @ManyToMany(mappedBy = "modules", fetch = FetchType.LAZY)
  private Set<AbstractUnit> abstractUnits;

  @OneToMany(mappedBy = "module")
  private Set<ModuleAbstractUnitSemester> moduleAbstractUnitSemesters;

  @OneToMany(mappedBy = "module")
  private Set<ModuleAbstractUnitType> moduleAbstractUnitTypes;

  /**
   * Get the maximum of credit points required for module. Returns -1 if the module
   * is not credit point based.
   *
   * @return int the maximum number of credit points for module.
   */

  public int getCreditPoints() {
    if (creditPoints == null) {
      return -1;
    }

    return creditPoints;
  }

  public void setCreditPoints(final Integer creditPoints) {
    this.creditPoints = creditPoints;
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
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

  public Boolean getMandatory() {
    return mandatory;
  }

  public void setMandatory(final Boolean mandatory) {
    this.mandatory = mandatory;
  }

  public Integer getElectiveUnits() {
    return electiveUnits;
  }

  public void setElectiveUnits(final Integer electiveUnits) {
    this.electiveUnits = electiveUnits;
  }

  public Date getUpdatedAt() {
    return (Date) updatedAt.clone();
  }

  public void setUpdatedAt(final Date updatedAt) {
    this.updatedAt = (Date) updatedAt.clone();
  }

  public Date getCreatedAt() {
    return (Date) createdAt.clone();
  }

  public void setCreatedAt(final Date createdAt) {
    this.createdAt = (Date) createdAt.clone();
  }

  public Set<Course> getCourses() {
    return courses;
  }

  public void setCourses(final Set<Course> courses) {
    this.courses = courses;
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
      && Objects.equals(name, module.name)
      && Objects.equals(title, module.title)
      && Objects.equals(pordnr, module.pordnr)
      && Objects.equals(mandatory, module.mandatory)
      && Objects.equals(electiveUnits, module.electiveUnits)
      && Objects.equals(creditPoints, module.creditPoints)
      && Objects.equals(updatedAt, module.updatedAt)
      && Objects.equals(createdAt, module.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, key, name, title, pordnr, mandatory, electiveUnits,
      creditPoints, updatedAt, createdAt);
  }
}
