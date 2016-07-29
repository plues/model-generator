package de.hhu.stups.plues.data.entities

import net.sf.ehcache.util.FindBugsSuppressWarnings
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Immutable
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Entity

@Entity
@Table(name="modules")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY,
        region="modules")
@Immutable
@FindBugsSuppressWarnings(["SE_NO_SERIALVERSIONID","SE_TRANSIENT_FIELD_NOT_RESTORED", "EI_EXPOSE_REP", "EI_EXPOSE_REP2"])
class Module implements Serializable {
    @Id
    @GeneratedValue
    int id
    @NaturalId
    private String key

    String name
    String title
    Integer pordnr
    Boolean mandatory
    Integer elective_units
    Integer credit_points
    @UpdateTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    Date updated_at
    @CreationTimestamp
    @Type(type="org.hibernate.usertype.SQLiteDateTimeType")
    Date created_at

    @ManyToMany(mappedBy = "modules", fetch=FetchType.LAZY)
    Set<Course> courses

    @ManyToMany(mappedBy="modules", fetch=FetchType.LAZY)
    Set<AbstractUnit> abstract_units

    @OneToMany(mappedBy="module")
    Set<ModuleAbstractUnitSemester> module_abstract_units_semester

    @OneToMany(mappedBy="module")
    Set<ModuleAbstractUnitType> module_abstract_units_types

    public Module() {}

    public int getCredit_points() {
        if(credit_points == null ) {
            return -1
        }
        return credit_points
    }
}
