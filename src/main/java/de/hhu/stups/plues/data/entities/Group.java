package de.hhu.stups.plues.data.entities;

import net.sf.ehcache.util.FindBugsSuppressWarnings;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "groups")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "groups")
@Immutable
@FindBugsSuppressWarnings({"SE_NO_SERIALVERSIONID", "SE_TRANSIENT_FIELD_NOT_RESTORED", "EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class Group implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Column(name="half_semester")
    private int halfSemester;

    @CreationTimestamp
    @Type(type = "org.hibernate.usertype.SQLiteDateTimeType")
    @Column(name="created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Type(type = "org.hibernate.usertype.SQLiteDateTimeType")
    @Column(name="updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "group")
    private Set<Session> sessions;

    @ManyToOne(fetch = FetchType.EAGER)
    private Unit unit;


    public Group() {
    }

    public String getHalfSemesterWord() {
        return (halfSemester == 1) ? "first" : "second";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHalfSemester() {
        return halfSemester;
    }

    public void setHalfSemester(int halfSemester) {
        this.halfSemester = halfSemester;
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

    public Set<Session> getSessions() {
        return sessions;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

}
