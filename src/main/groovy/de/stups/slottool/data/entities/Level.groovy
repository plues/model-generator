package de.stups.slottool.data.entities

import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table


@javax.persistence.Entity
@Table(name="levels")
class Level {
    @Id
    Integer id
    String art
    String name
    String tm
    private Integer max
    private Integer min
    private Integer min_credit_points
    private Integer max_credit_points

    Date updated_at
    Date created_at


    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="modules_levels",
            joinColumns=@JoinColumn(name="level_id", referencedColumnName = "id"),
            inverseJoinColumns=@JoinColumn(name="module_id", referencedColumnName = "id"))
    Set<Module> modules // has many

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="parent_id")
    Level parent

    @OneToMany(mappedBy="parent")
    private Set<Level> children // has many both are exclusive

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinTable(
            name="course_levels",
            inverseJoinColumns=@JoinColumn(name="course_id", referencedColumnName="id"),
            joinColumns=@JoinColumn(name="level_id", referencedColumnName="id"))
    Course course // belongs to one course

    def Level() {}

    @SuppressWarnings("GroovyUnusedDeclaration")
    def getMin() {
        if(this.min == null) {
            return -1
        }
        return this.min
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    def getMax() {
        if(this.max == null) {
            return -1
        }
        return this.max

    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    def getMax_credit_points() {
        if(this.max_credit_points == null) {
            return -1
        }
        return this.max_credit_points
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    def getMin_credit_points() {
        if(this.min_credit_points == null) {
            return -1
        }
        return this.min_credit_points
    }
}
