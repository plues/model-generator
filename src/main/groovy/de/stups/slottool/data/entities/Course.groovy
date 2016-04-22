package de.stups.slottool.data.entities

import org.hibernate.annotations.NaturalId

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
class Course {
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

    Date created_at
    Date updated_at

    @ManyToMany(fetch = FetchType.LAZY)
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
}
