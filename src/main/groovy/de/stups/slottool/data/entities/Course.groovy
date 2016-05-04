package de.stups.slottool.data.entities

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.Column
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@javax.persistence.Entity
@Table(name="courses")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY,
        region="courses")
class Course implements Serializable {
    @Id
    @GeneratedValue
    int id
    @NaturalId
    String key

    Integer po
    Integer credit_points

    String short_name
    @Column(name = "name")
    String long_name
    String degree
    String kzfa

    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    @CreationTimestamp
    Date created_at

    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    @UpdateTimestamp
    Date updated_at

    @ManyToMany()
    @JoinTable(
            name="course_modules",
            joinColumns=@JoinColumn(name="course_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="module_id", referencedColumnName="id"))
    Set<Module> modules

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="course_levels",
            joinColumns=@JoinColumn(name="course_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="level_id", referencedColumnName="id"))
    Set<Level> levels

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<ModuleCombination> module_combinations

    def Course() {}


    @SuppressWarnings("GroovyUnusedDeclaration")
    def getName() {
        return this.key
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    def getFullName() {
        return "${this.long_name} (${this.degree} ${this.kzfa}) PO:${this.po}"
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    def getCredit_points() {
        if(credit_points == null) {
            return -1
        }
        return credit_points
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    def isMajor() {
        this.kzfa == "H"
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    def isMinor() {
        this.kzfa == "N"
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    def isCombinable() {
        this.degree == "bk" // bk is combinable ba is not
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    def getModuleCombinations() {
        def combinations = [:]
        module_combinations.each { mc ->
            if(!combinations.containsKey(mc.combination_id)) {
                combinations[mc.combination_id] = []
            }
            combinations[mc.combination_id] << mc.module_id
        }
        return combinations.values()
    }

    static enum KZFA {
        static def MINOR = "N"
        static def MAJOR = "H"
    }
}
