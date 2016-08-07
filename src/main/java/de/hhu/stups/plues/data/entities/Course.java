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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "courses")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "courses")
@Immutable
public class Course implements Serializable {

    private static final long serialVersionUID = 5212299499702133835L;

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

    @Type(type = "org.hibernate.usertype.SQLiteDateTimeType")
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @Type(type = "org.hibernate.usertype.SQLiteDateTimeType")
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
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<ModuleCombination> moduleCombinations;


    public Course() {
    }

    public String getName() {
        return this.key;
    }

    public String getFullName() {
        return this.longName + " (" + this.degree + " " + this.kzfa + ") PO:" + String.valueOf(this.po);
    }

    public int getCreditPoints() {
        if(creditPoints == null) {
            return -1;
        }

        return creditPoints;
    }

    public boolean isMajor() {
        return this.kzfa.equals("H");
    }

    public boolean isMinor() {
        return this.kzfa.equals("N");
    }

    public boolean isCombinable() {
        return this.degree.equals("bk");// bk is combinable ba is not
    }

    public List<List<Integer>> getModuleCombinations() {
        final Map<Integer, List<Integer>> combinations = new HashMap<>();
        moduleCombinations.forEach(mc -> {
            if(!combinations.containsKey(mc.getCombinationId())) {
                combinations.put(mc.getCombinationId(), new ArrayList<>());
            }
            combinations.get(mc.getCombinationId()).add(mc.getModuleId());
        });
        return new ArrayList<>(combinations.values());
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

    public void setCreditPoints(final Integer creditPoints) {
        this.creditPoints = creditPoints;
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
        return createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
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
}
