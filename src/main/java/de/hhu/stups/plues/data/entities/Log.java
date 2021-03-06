package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "log")
public class Log implements Serializable {

  private static final long serialVersionUID = 8715213161059401044L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(fetch = FetchType.EAGER)
  private Session session;

  private String srcDay;

  private Integer srcTime;

  private String targetDay;

  private Integer targetTime;

  @CreationTimestamp
  @Type(type = "org.hibernate.usertype.SqliteDateTimeType")
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  public Log() {
    // Default constructor is required by hibernate
  }

  public int getId() {
    return id;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(final Session session) {
    this.session = session;
  }

  public String getSrcDay() {
    return srcDay;
  }

  public Integer getSrcTime() {
    return srcTime;
  }

  public void setSource(final String srcDay, final Integer srcTime) {
    this.srcDay = srcDay;
    this.srcTime = srcTime;
  }

  public String getTargetDay() {
    return targetDay;
  }

  public Integer getTargetTime() {
    return targetTime;
  }

  public void setTarget(final String targetDay, final Integer targetTime) {
    this.targetDay = targetDay;
    this.targetTime = targetTime;
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
        && Objects.equals(createdAt, log.createdAt)
        && equalsSource(log)
        && equalsTarget(log);
  }

  private boolean equalsSource(final Log log) {
    return Objects.equals(srcDay, log.srcDay) && Objects.equals(srcTime, log.srcTime);
  }

  private boolean equalsTarget(final Log log) {
    return Objects.equals(targetDay, log.targetDay) && Objects.equals(targetTime, log.targetTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(session, srcDay, srcTime, targetDay, targetTime, createdAt);
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(final LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
