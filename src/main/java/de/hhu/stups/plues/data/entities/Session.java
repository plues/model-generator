package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
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

  private static final Map<String, DayOfWeek> dayOfWeekMap = new HashMap<>();
  private static final Map<DayOfWeek, String> dayMap = new EnumMap<>(DayOfWeek.class);

  static {
    initMaps();
  }

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


  private static void initMaps() {
    dayOfWeekMap.put("mon", DayOfWeek.MONDAY);
    dayOfWeekMap.put("tue", DayOfWeek.TUESDAY);
    dayOfWeekMap.put("wed", DayOfWeek.WEDNESDAY);
    dayOfWeekMap.put("thu", DayOfWeek.THURSDAY);
    dayOfWeekMap.put("fri", DayOfWeek.FRIDAY);

    dayMap.put(DayOfWeek.MONDAY, "mon");
    dayMap.put(DayOfWeek.TUESDAY, "tue");
    dayMap.put(DayOfWeek.WEDNESDAY, "wed");
    dayMap.put(DayOfWeek.THURSDAY, "thu");
    dayMap.put(DayOfWeek.FRIDAY, "fri");
  }

  public Map<DayOfWeek, String> getDayMap() {
    return dayMap;
  }

  public Map<String, DayOfWeek> getDayOfWeekMap() {
    return dayOfWeekMap;
  }

  public DayOfWeek getDayOfWeek() {
    final DayOfWeek dayOfWeek = dayOfWeekMap.get(getDay());
    return (dayOfWeek == null) ? DayOfWeek.SUNDAY : dayOfWeek;
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
    return String.format("%s (%s/%d)",
        group.getUnit().getTitle(), group.getUnit().getKey(), group.getId());
  }
}
