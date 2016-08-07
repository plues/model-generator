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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "abstract_units")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "abstract_units")
@Immutable
public class AbstractUnit implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @NaturalId
    private String key;

    private String title;

    @SuppressWarnings("GroovyUnusedDeclaration")
    @CreationTimestamp
    @Type(type = "org.hibernate.usertype.SQLiteDateTimeType")
    @Column(name = "created_at")
    private Date createdAt;

    @SuppressWarnings("GroovyUnusedDeclaration")
    @UpdateTimestamp
    @Type(type = "org.hibernate.usertype.SQLiteDateTimeType")
    @Column(name = "updated_at")
    private Date updatedAt;

    @SuppressWarnings("GroovyUnusedDeclaration")
    @ManyToMany
    @JoinTable(name = "unit_abstract_unit", joinColumns = @JoinColumn(name = "abstract_unit_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "unit_id", referencedColumnName = "id"))
    private Set<Unit> units;

    @SuppressWarnings("GroovyUnusedDeclaration")
    @OneToMany(mappedBy = "abstractUnit")
    private Set<ModuleAbstractUnitSemester> moduleAbstractUnitSemesters;

    @SuppressWarnings("GroovyUnusedDeclaration")
    @OneToMany(mappedBy = "abstractUnit")
    private Set<ModuleAbstractUnitType> moduleAbstractUnitTypes;

    @SuppressWarnings("GroovyUnusedDeclaration")
    @ManyToMany
    @JoinTable(name = "modules_abstract_units_types", joinColumns = @JoinColumn(name = "abstract_unit_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "module_id", referencedColumnName = "id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "modules")
    private Set<Module> modules;

    public AbstractUnit() {
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
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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

    public void setModuleAbstractUnitSemesters(Set<ModuleAbstractUnitSemester> moduleAbstractUnitSemesters) {
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
