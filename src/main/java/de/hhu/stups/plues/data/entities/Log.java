package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
  @Type(type = "org.hibernate.usertype.SqliteDateTimeType")
  @Column(name = "created_at")
  private Date createdAt;

  public Log() {
    // Default constructor is required by hibernate
  }

  public Session getSession() {
    return session;
  }

  public void setSession(final Session session) {
    this.session = session;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(final String src) {
    this.src = src;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(final String target) {
    this.target = target;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    final Log log = (Log) other;
    return Objects.equals(session, log.session)
        && Objects.equals(src, log.src)
        && Objects.equals(target, log.target)
        && Objects.equals(createdAt, log.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(session, src, target, createdAt);
  }

  public Date getCreatedAt() {
    return (Date) createdAt.clone();
  }

  public void setCreatedAt(final Date createdAt) {
    this.createdAt = (Date) createdAt.clone();
  }
}
