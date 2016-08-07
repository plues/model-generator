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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "groups")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "groups")
@Immutable
public class Group implements Serializable {

    private static final long serialVersionUID = -2048455700838010908L;
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
        return (Date) createdAt.clone();
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = (Date) createdAt.clone();
    }

    public Date getUpdatedAt() {
        return (Date) updatedAt.clone();
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = (Date) updatedAt.clone();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id &&
                halfSemester == group.halfSemester &&
                Objects.equals(createdAt, group.createdAt) &&
                Objects.equals(updatedAt, group.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, halfSemester, createdAt, updatedAt);
    }
}
