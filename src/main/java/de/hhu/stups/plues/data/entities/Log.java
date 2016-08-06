package de.hhu.stups.plues.data.entities;

import net.sf.ehcache.util.FindBugsSuppressWarnings;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "log")
@FindBugsSuppressWarnings({"SE_TRANSIENT_FIELD_NOT_RESTORED", "EQ_UNUSUAL", "SE_NO_SERIALVERSIONID", "EI_EXPOSE_REP2", "EI_EXPOSE_REP"})
public class Log implements Serializable {
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

    public boolean equals(Object o) {
        if(DefaultGroovyMethods.is(this, o)) return true;
        if(!getClass().equals(o.getClass())) return false;

        Log log = (Log) o;

        if(!createdAt.equals(log.createdAt)) return false;
        if(!session.equals(log.session)) return false;
        if(!src.equals(log.src)) return false;
        if(!target.equals(log.target)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = ((int) ((session != null ? session.hashCode() : 0)));
        result = ((int) (31 * result + (src != null ? src.hashCode() : 0)));
        result = ((int) (31 * result + (target != null ? target.hashCode() : 0)));
        result = ((int) (31 * result + (createdAt != null ? createdAt.hashCode() : 0)));
        return result;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
