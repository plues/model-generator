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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "courses")
@Immutable
public class Course implements Serializable {

  private static final long serialVersionUID = 4805589618641984556L;

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

  @Type(type = "org.hibernate.usertype.SqliteDateTimeType")
  @CreationTimestamp
  @Column(name = "created_at")
  private Date createdAt;

  @Type(type = "org.hibernate.usertype.SqliteDateTimeType")
  @UpdateTimestamp
  @Column(name = "updated_at")
  private Date updatedAt;

  @ManyToMany
  @JoinTable(name = "course_modules",
      joinColumns = @JoinColumn(name = "course_id",
          referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "module_id",
          referencedColumnName = "id"))
  private Set<Module> modules;

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
      && equalTimestamps(course);
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

  private boolean equalTimestamps(final Course course) {
    return Objects.equals(this.createdAt, course.createdAt)
      && Objects.equals(this.updatedAt, course.updatedAt);
  }

  private boolean equalIdentifiers(final Course course) {
    return this.id == course.id && Objects.equals(this.key, course.key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.key, this.po, this.creditPoints,
        this.shortName, this.longName, this.degree,
        this.kzfa, this.createdAt, this.updatedAt);
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

  public Set<Module> getModules() {
    return modules;
  }

  public void setModules(final Set<Module> modules) {
    this.modules = modules;
  }

  public Set<Level> getLevels() {
    return levels;
  }

  public void setLevels(final Set<Level> levels) {
    this.levels = levels;
  }

  public boolean isCombinableWith(final Course other) {
    return this.isCombinable() && other.isCombinable()
      && !this.getShortName().equals(other.getShortName());
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
