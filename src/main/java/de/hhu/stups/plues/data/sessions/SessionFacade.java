package de.hhu.stups.plues.data.sessions;

import de.hhu.stups.plues.data.entities.Session;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Transient;

public class SessionFacade implements Serializable {
  private static final long serialVersionUID = 6459045002667850077L;

  private final Session session;

  private static final Map<String, DayOfWeek> dayOfWeekMap = new HashMap<>();
  private static final Map<DayOfWeek, String> dayMap = new EnumMap<>(DayOfWeek.class);

  static {
    initMaps();
  }

  @Transient
  private final transient ObjectProperty<Slot> slotObjectProperty = new SimpleObjectProperty<>();

  public SessionFacade(final Session session) {
    this.session = session;
    initSlotProperty();
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

  private DayOfWeek getDayOfWeek() {
    final DayOfWeek day = dayOfWeekMap.get(session.getDay());

    if (day == null) {
      return DayOfWeek.SUNDAY;
    }

    return day;
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

  /**
   * Set slot of session.
   *
   * @param slot the slot to set
   */
  public void setSlot(final Slot slot) {
    session.setDay(slot.getDayString());
    session.setTime(slot.time);
    Platform.runLater(() -> slotObjectProperty.set(new Slot(getDayOfWeek(), session.getTime())));
  }

  public Session getSession() {
    return session;
  }

  public static class Slot {
    private final DayOfWeek day;
    private final Integer time;

    public Slot(final DayOfWeek day, final Integer time) {
      this.day = day;
      this.time = time;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == null || !(obj instanceof Slot)) {
        return false;
      }
      if (obj == this) {
        return true;
      }

      final Slot slot = (Slot) obj;

      return slot.day.equals(day) && slot.time.equals(time);
    }

    @Override
    public int hashCode() {
      return Objects.hash(day, time);
    }

    /**
     * Returns the string representation of the day of this slot.
     *
     * @return String representation of the day
     */
    public String getDayString() {
      final String dayString = dayMap.get(day);

      if (dayString == null) {
        return "sun";
      }

      return dayString;
    }

    @Override
    public String toString() {
      return String.format("%s: %d", day.toString(), time);
    }

    public Integer getTime() {
      return time;
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
