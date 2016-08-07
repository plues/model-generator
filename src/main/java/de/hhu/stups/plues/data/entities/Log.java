package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "log")
public class Log implements Serializable {
    private static final long serialVersionUID = 8715213161059401044L;
    @ManyToOne(fetch = FetchType.EAGER)
    @Id
    private Session session;

    @Id
    private String src;

    @Id
    private String target;

    @CreationTimestamp
    @Type(type = "org.hibernate.usertype.SQLiteDateTimeType")
    @Column(name = "created_at")
    private Date createdAt;

    public Log() {
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return Objects.equals(session, log.session) &&
                Objects.equals(src, log.src) &&
                Objects.equals(target, log.target) &&
                Objects.equals(createdAt, log.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, src, target, createdAt);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
