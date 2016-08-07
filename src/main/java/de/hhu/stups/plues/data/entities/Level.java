package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "levels")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "leves")
@Immutable
public class Level implements Serializable {
    private static final long serialVersionUID = -7800840594086486428L;
    @Id
    private Integer id;
    private String art;
    private String name;
    private String tm;
    private Integer max;
    private Integer min;

    @Column(name="min_credit_points")
    private Integer minCreditPoints;

    @Column(name="max_credit_points")
    private Integer maxCreditPoints;

    @CreationTimestamp
    @Type(type = "org.hibernate.usertype.SQLiteDateTimeType")
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Type(type = "org.hibernate.usertype.SQLiteDateTimeType")
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "module_levels",
            joinColumns = @JoinColumn(name = "level_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "module_id",
                    referencedColumnName = "id"))
    private Set<Module> modules;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Level parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private Set<Level> children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "course_levels",
            inverseJoinColumns = @JoinColumn(name = "course_id",
                    referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "level_id",
                    referencedColumnName = "id"))
    private Course course;

    public Level() {
    }

    public int getMin() {
        if(this.min == null) {
            return -1;
        }

        return this.min;
    }

    public int getMax() {
        if(this.max == null) {
            return -1;
        }

        return this.max;

    }

    public int getMaxCreditPoints() {
        if(this.maxCreditPoints == null) {
            return -1;
        }

        return this.maxCreditPoints;
    }

    public int getMinCreditPoints() {
        if(this.minCreditPoints == null) {
            return -1;
        }

        return this.minCreditPoints;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
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

    public Set<Module> getModules() {
        return modules;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }

    public Level getParent() {
        return parent;
    }

    public void setParent(Level parent) {
        this.parent = parent;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    public Set<Level> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level level = (Level) o;
        return Objects.equals(id, level.id) &&
                Objects.equals(art, level.art) &&
                Objects.equals(name, level.name) &&
                Objects.equals(tm, level.tm) &&
                Objects.equals(max, level.max) &&
                Objects.equals(min, level.min) &&
                Objects.equals(minCreditPoints, level.minCreditPoints) &&
                Objects.equals(maxCreditPoints, level.maxCreditPoints) &&
                Objects.equals(createdAt, level.createdAt) &&
                Objects.equals(updatedAt, level.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, art, name, tm, max, min, minCreditPoints, maxCreditPoints, createdAt, updatedAt);
    }
}
