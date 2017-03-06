package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "module_levels")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "module_levels")
@Immutable
public class ModuleLevel extends ModelEntity implements Serializable {

  private static final long serialVersionUID = 1918247818562995682L;

  @Id
  @GeneratedValue
  private int id;

  @ManyToOne
  @JoinColumn(name = "module_id")
  private Module module;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  @ManyToOne
  @JoinColumn(name = "level_id")
  private Level level;

  private String name;

  private Boolean mandatory;

  @Column(name = "credit_points")
  private Integer creditPoints;

  public ModuleLevel() {
    // Default constructor is required by hibernate
  }

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

  public Boolean getMandatory() {
    return mandatory;
  }

  public Module getModule() {
    return module;
  }

  public Course getCourse() {
    return course;
  }

  public int getId() {
    return id;
  }

  public Level getLevel() {
    return level;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    if (!super.equals(other)) {
      return false;
    }
    final ModuleLevel that = (ModuleLevel) other;
    return id == that.id
        && Objects.equals(name, that.name)
        && Objects.equals(mandatory, that.mandatory)
        && Objects.equals(creditPoints, that.creditPoints);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, name, mandatory, creditPoints);
  }
}
