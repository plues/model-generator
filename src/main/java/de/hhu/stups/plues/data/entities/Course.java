package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "courses")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "courses")
@Immutable
public class Course extends ModelEntity implements Serializable {

  private static final long serialVersionUID = 935565328177496460L;

  @Id
  @GeneratedValue
  private int id;

  @NaturalId
  private String key;
  private Integer po;

  @Column(name = "credit_points")
  private Integer creditPoints;

  @Column(name = "short_name")
  private String shortName;

  @Column(name = "name")
  private String longName;

  private String degree;

  private String kzfa;

  @ManyToMany
  @JoinTable(name = "module_levels",
      joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "module_id", referencedColumnName = "id"))
  @Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "course_module_levels")
  private Set<Module> modules;

  @OneToMany
  @JoinTable(name = "minors",
      joinColumns = @JoinColumn(name = "course_id"),
      inverseJoinColumns = @JoinColumn(name = "minor_course_id"))
  private Set<Course> minorCourses;

  @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
  private Set<ModuleLevel> moduleLevels;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "course_levels",
      joinColumns = @JoinColumn(name = "course_id",
          referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "level_id",
          referencedColumnName = "id"))
  private Set<Level> levels;

  @SuppressWarnings("unused")
  @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
  private Set<ModuleCombination> moduleCombinations;

  public Course() {
    // Default constructor is required by hibernate
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || this.getClass() != other.getClass()) {
      return false;
    }
    final Course course = (Course) other;
    return equalIdentifiers(course)
        && equalNames(course)
        && equalCourseAttributes(course)
        && super.equals(other);
  }

  private boolean equalCourseAttributes(final Course course) {
    return Objects.equals(this.po, course.po)
        && Objects.equals(this.creditPoints, course.creditPoints)
        && Objects.equals(this.degree, course.degree)
        && Objects.equals(this.kzfa, course.kzfa);
  }

  private boolean equalNames(final Course course) {
    return Objects.equals(this.shortName, course.shortName)
        && Objects.equals(this.longName, course.longName);
  }

  private boolean equalIdentifiers(final Course course) {
    return this.id == course.id && Objects.equals(this.key, course.key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.key, this.po, this.creditPoints,
        this.shortName, this.longName, this.degree,
        this.kzfa, super.hashCode());
  }

  public String getName() {
    return this.key;
  }

  public String getFullName() {
    return this.longName + " (" + this.degree + " " + this.kzfa + ") PO:" + this.po;
  }

  /**
   * Number of credit points required to complete the course.
   * Return value is < 0 if the course is not credit point based.
   *
   * @return int points, negative if course is not credit point based
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

  public boolean isMajor() {
    return "H".equalsIgnoreCase(this.kzfa);
  }

  public boolean isMinor() {
    return "N".equalsIgnoreCase(this.kzfa);
  }

  public boolean isCombinable() {
    return "bk".equalsIgnoreCase(this.degree);// bk is combinable ba is not
  }

  @SuppressWarnings("unused")
  public Set<ModuleCombination> getModuleCombinations() {
    return this.moduleCombinations;
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

  public Integer getPo() {
    return po;
  }

  public void setPo(final Integer po) {
    this.po = po;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(final String shortName) {
    this.shortName = shortName;
  }

  public String getLongName() {
    return longName;
  }

  public void setLongName(final String longName) {
    this.longName = longName;
  }

  /* Used for testing */
  public void setMinorCourses(final Set<Course> minorCourses) {
    this.minorCourses = minorCourses;
  }

  public String getDegree() {
    return degree;
  }

  public void setDegree(final String degree) {
    this.degree = degree;
  }

  public String getKzfa() {
    return kzfa;
  }

  public void setKzfa(final String kzfa) {
    this.kzfa = kzfa;
  }

  public Set<Module> getModules() {
    return modules;
  }

  public Set<ModuleLevel> getModuleLevels() {
    return moduleLevels;
  }

  public void setModuleLevels(final Set<ModuleLevel> moduleLevels) {
    this.moduleLevels = moduleLevels;
  }

  public Set<Course> getMinorCourses() {
    return minorCourses;
  }

  public Set<Level> getLevels() {
    return levels;
  }

  public void setLevels(final Set<Level> levels) {
    this.levels = levels;
  }

  public boolean isBachelor() {
    return "ba".equals(degree) || "bk".equals(degree);
  }

  public boolean isMaster() {
    return !isBachelor();
  }

  @Override
  public String toString() {
    return this.getFullName();
  }
}
