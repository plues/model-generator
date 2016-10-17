package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sessions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "session")
public class Session implements Serializable {

  private static final long serialVersionUID = 7760428839071975511L;

  @Id
  @GeneratedValue
  private int id;

  private String day;
  private Integer time;
  private Integer rhythm;
  private Integer duration;

  @SuppressWarnings("unused")
  private boolean tentative;

  @CreationTimestamp
  @Type(type = "org.hibernate.usertype.SqliteDateTimeType")
  @Column(name = "created_at")
  private Date createdAt;

  @UpdateTimestamp
  @Type(type = "org.hibernate.usertype.SqliteDateTimeType")
  @Column(name = "updated_at")
  private Date updatedAt;

  @ManyToOne(fetch = FetchType.EAGER)
  private Group group;

  public Session() {
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getDay() {
    return day;
  }

  public void setDay(final String day) {
    this.day = day;
  }

  public Integer getTime() {
    return time;
  }

  public void setTime(final Integer time) {
    this.time = time;
  }

  public Integer getRhythm() {
    return rhythm;
  }

  public void setRhythm(final Integer rhythm) {
    this.rhythm = rhythm;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(final Integer duration) {
    this.duration = duration;
  }

  public Date getCreatedAt() {
    return (Date) createdAt.clone();
  }

  public void setCreatedAt(final Date createdAt) {
    this.createdAt = (Date) createdAt.clone();
  }

  public Date getUpdatedAt() {
    return (Date) updatedAt.clone();
  }

  public void setUpdatedAt(final Date updatedAt) {
    this.updatedAt = (Date) updatedAt.clone();
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(final Group group) {
    this.group = group;
  }

  @SuppressWarnings("unused")
  public boolean isTentative() {
    return tentative;
  }


  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    final Session session = (Session) other;
    return id == session.id
        && Objects.equals(day, session.day)
        && Objects.equals(time, session.time)
        && Objects.equals(rhythm, session.rhythm)
        && Objects.equals(duration, session.duration)
        && Objects.equals(createdAt, session.createdAt)
        && Objects.equals(updatedAt, session.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, day, time, rhythm, duration, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    return String.format("%s (%d/%d)",
        group.getUnit().getTitle(), group.getId(), group.getUnit().getId());
  }
}
