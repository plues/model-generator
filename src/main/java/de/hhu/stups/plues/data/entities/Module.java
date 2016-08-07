package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "modules")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "modules")
@Immutable
public class Module implements Serializable {
    private static final long serialVersionUID = -9153665188193235995L;

    public Module() {
    }

    public int getCreditPoints() {
        if(creditPoints == null) {
            return -1;
        }

        return creditPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPordnr() {
        return pordnr;
    }

    public void setPordnr(Integer pordnr) {
        this.pordnr = pordnr;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Integer getElectiveUnits() {
        return electiveUnits;
    }

    public void setElectiveUnits(Integer electiveUnits) {
        this.electiveUnits = electiveUnits;
    }

    public void setCreditPoints(Integer creditPoints) {
        this.creditPoints = creditPoints;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<AbstractUnit> getAbstract_units() {
        return abstract_units;
    }

    public void setAbstract_units(Set<AbstractUnit> abstract_units) {
        this.abstract_units = abstract_units;
    }

    public Set<ModuleAbstractUnitSemester> getModuleAbstractUnitSemesters() {
        return moduleAbstractUnitSemesters;
    }

    public void setModuleAbstractUnitSemesters(Set<ModuleAbstractUnitSemester> moduleAbstractUnitSemesters) {
        this.moduleAbstractUnitSemesters = moduleAbstractUnitSemesters;
    }

    public Set<ModuleAbstractUnitType> getModuleAbstractUnitTypes() {
        return moduleAbstractUnitTypes;
    }

    public void setModuleAbstractUnitTypes(Set<ModuleAbstractUnitType> moduleAbstractUnitTypes) {
        this.moduleAbstractUnitTypes = moduleAbstractUnitTypes;
    }

    @Id
    @GeneratedValue
    private int id;

    @NaturalId
    private String key;
    private String name;
    private String title;
    private Integer pordnr;
    private Boolean mandatory;
    @Column(name="elective_units")
    private Integer electiveUnits;

    @Column(name="credit_points")
    private Integer creditPoints;

    @UpdateTimestamp
    @Type(type = "org.hibernate.usertype.SQLiteDateTimeType")
    @Column(name="updated_at")
    private Date updatedAt;

    @CreationTimestamp
    @Type(type = "org.hibernate.usertype.SQLiteDateTimeType")
    @Column(name="created_at")
    private Date createdAt;

    @ManyToMany(mappedBy = "modules", fetch = FetchType.LAZY)
    private Set<Course> courses;

    @ManyToMany(mappedBy = "modules", fetch = FetchType.LAZY)
    private Set<AbstractUnit> abstract_units;

    @OneToMany(mappedBy = "module")
    private Set<ModuleAbstractUnitSemester> moduleAbstractUnitSemesters;

    @OneToMany(mappedBy = "module")
    private Set<ModuleAbstractUnitType> moduleAbstractUnitTypes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return id == module.id &&
                Objects.equals(key, module.key) &&
                Objects.equals(name, module.name) &&
                Objects.equals(title, module.title) &&
                Objects.equals(pordnr, module.pordnr) &&
                Objects.equals(mandatory, module.mandatory) &&
                Objects.equals(electiveUnits, module.electiveUnits) &&
                Objects.equals(creditPoints, module.creditPoints) &&
                Objects.equals(updatedAt, module.updatedAt) &&
                Objects.equals(createdAt, module.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, name, title, pordnr, mandatory, electiveUnits, creditPoints, updatedAt, createdAt);
    }
}
