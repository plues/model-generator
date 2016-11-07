package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "modules_abstract_units_semesters")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "modules_abstract_units_semesters")
@Immutable
public class ModuleAbstractUnitSemester implements Serializable {

  private static final long serialVersionUID = 9021333343897176890L;
  @Id
  @ManyToOne
  private Module module;

  @Id
  @ManyToOne
  @JoinColumn(name = "abstract_unit_id")
  private AbstractUnit abstractUnit;

  @Id
  private Integer semester;

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    ModuleAbstractUnitSemester that = (ModuleAbstractUnitSemester) other;
    return Objects.equals(module, that.module)
        && Objects.equals(abstractUnit, that.abstractUnit)
        && Objects.equals(semester, that.semester);
  }

  @Override
  public int hashCode() {
    return Objects.hash(module, abstractUnit, semester);
  }

  public Module getModule() {
    return module;
  }

  public void setModule(final Module module) {
    this.module = module;
  }

  public AbstractUnit getAbstractUnit() {
    return abstractUnit;
  }

  public void setAbstractUnit(final AbstractUnit abstractUnit) {
    this.abstractUnit = abstractUnit;
  }

  public Integer getSemester() {
    return semester;
  }

  public void setSemester(final Integer semester) {
    this.semester = semester;
  }
}
