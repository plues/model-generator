package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "sessions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "session")
public class Session extends ModelEntity implements Serializable {

  private static final long serialVersionUID = 3790646138346596774L;

  private static final Map<String, DayOfWeek> dayOfWeekMap = new HashMap<>();

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

  @ManyToOne(fetch = FetchType.EAGER)
  private Group group;

  public Session() {
    // Default constructor is required by hibernate
  }

  private static void initMaps() {
    dayOfWeekMap.put("mon", DayOfWeek.MONDAY);
    dayOfWeekMap.put("tue", DayOfWeek.TUESDAY);
    dayOfWeekMap.put("wed", DayOfWeek.WEDNESDAY);
    dayOfWeekMap.put("thu", DayOfWeek.THURSDAY);
    dayOfWeekMap.put("fri", DayOfWeek.FRIDAY);
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
        && super.equals(session);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, day, time, rhythm, duration, super.hashCode());
  }

  @Override
  public String toString() {
    return String.format("%s (%s/%d)",
        group.getUnit().getTitle(), group.getUnit().getKey(), group.getId());
  }
}
