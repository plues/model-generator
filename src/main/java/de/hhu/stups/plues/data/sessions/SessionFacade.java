package de.hhu.stups.plues.data.sessions;

import de.hhu.stups.plues.data.entities.Session;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Transient;

public class SessionFacade implements Serializable {
  private final Session session;

  @Transient
  public final transient ObjectProperty<Slot> slotObjectProperty = new SimpleObjectProperty<>();

  public SessionFacade(Session session) {
    this.session = session;
    initSlotProperty();
  }

  public DayOfWeek getDayOfWeek() {
    if (session.getDay() == null) {
      return DayOfWeek.SUNDAY;
    }

    switch (session.getDay()) {
      case "mon":
        return DayOfWeek.MONDAY;
      case "tue":
        return DayOfWeek.TUESDAY;
      case "wed":
        return DayOfWeek.WEDNESDAY;
      case "thu":
        return DayOfWeek.THURSDAY;
      case "fri":
        return DayOfWeek.FRIDAY;
      default:
        return DayOfWeek.SUNDAY;
    }
  }

  public void initSlotProperty() {
    slotObjectProperty.set(new Slot(getDayOfWeek(), session.getTime()));
  }

  public ObjectProperty<Slot> slotProperty() {
    return slotObjectProperty;
  }

  public Slot getSlot() {
    return slotObjectProperty.get();
  }

  public void setSlot(Slot slot) {
    session.setDay(slot.getDayString());
    session.setTime(slot.time);
    Platform.runLater(() -> slotObjectProperty.set(new Slot(getDayOfWeek(), session.getTime())));
  }

  public Session getSession() {
    return session;
  }

  public static class Slot {
    public DayOfWeek day;
    public Integer time;

    public Slot(DayOfWeek day, Integer time) {
      this.day = day;
      this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null || !(obj instanceof Slot)) {
        return false;
      }
      if (obj == this) {
        return true;
      }

      Slot slot = (Slot) obj;

      return slot.day.equals(day) && slot.time.equals(time);
    }

    @Override
    public int hashCode() {
      return Objects.hash(day, time);
    }

    public String getDayString() {
      switch (day) {
        case MONDAY:
          return "mon";
        case TUESDAY:
          return "tue";
        case WEDNESDAY:
          return "wed";
        case THURSDAY:
          return "thu";
        case FRIDAY:
          return "fri";
        default:
          return "sun";
      }
    }
  }

  @Override
  public String toString() {
    return session.toString();
  }

  public Set<Integer> getSemesters() {
    return session.getGroup().getUnit().getSemesters();
  }
}
