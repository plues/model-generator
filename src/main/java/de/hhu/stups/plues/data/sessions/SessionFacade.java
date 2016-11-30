package de.hhu.stups.plues.data.sessions;

import de.hhu.stups.plues.data.entities.AbstractUnit;
import de.hhu.stups.plues.data.entities.ModuleAbstractUnitSemester;
import de.hhu.stups.plues.data.entities.Session;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SessionFacade {
  private final Session session;

  private final ObjectProperty<Slot> slotObjectProperty = new SimpleObjectProperty<>();

  private final BooleanProperty tentative = new SimpleBooleanProperty();

  /**
   * A class that facades a session for ui representation.
   *
   * @param session the session to facade
   */
  public SessionFacade(final Session session) {
    this.session = session;

    setTentative(this.session.isTentative());

    initSlotProperty();
  }

  private DayOfWeek getDayOfWeek() {
    return session.getDayOfWeek();
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

  public List<String> getAbstractUnitKeys() {
    return session.getGroup().getUnit().getAbstractUnits().stream().map(AbstractUnit::getKey)
        .collect(Collectors.toList());
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

  public int getId() {
    return session.getId();
  }

  public String getUnitKey() {
    return session.getGroup().getUnit().getKey();
  }

  public Integer getGroupId() {
    return session.getGroup().getId();
  }

  public static class Slot {
    private final DayOfWeek day;
    private final Integer time;

    private final Map<DayOfWeek, String> dayMap = new EnumMap<>(DayOfWeek.class);
    private final Map<Integer, String> timeMap = new HashMap<>();

    /**
     * Create a new Slot object.
     * @param day DayOfWeek for the slot
     * @param time integer representing the time slot
     */
    public Slot(final DayOfWeek day, final Integer time) {
      this.day = day;
      this.time = time;

      if (Locale.getDefault().equals(Locale.GERMANY)) {
        dayMap.put(DayOfWeek.MONDAY, "Montag");
        dayMap.put(DayOfWeek.TUESDAY, "Dienstag");
        dayMap.put(DayOfWeek.WEDNESDAY, "Mittwoch");
        dayMap.put(DayOfWeek.THURSDAY, "Donnerstag");
        dayMap.put(DayOfWeek.FRIDAY, "Freitag");
      } else {
        dayMap.put(DayOfWeek.MONDAY, "Monday");
        dayMap.put(DayOfWeek.TUESDAY, "Tuesday");
        dayMap.put(DayOfWeek.WEDNESDAY, "Wednesday");
        dayMap.put(DayOfWeek.THURSDAY, "Thursday");
        dayMap.put(DayOfWeek.FRIDAY, "Friday");
      }

      for (int i = 1 ; i <= 7; i++) {
        timeMap.put(i, String.valueOf(6 + i * 2) + ":30");
      }
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

    private String getTimeString() {
      if (!timeMap.containsKey(time)) {
        return "00:00";
      }

      return timeMap.get(time);
    }

    @Override
    public String toString() {
      return String.format("%s, %s", getDayString(), getTimeString());
    }

    public Integer getTime() {
      return time;
    }
  }

  public boolean isTentative() {
    return tentative.get();
  }

  public BooleanProperty tentativeProperty() {
    return tentative;
  }

  private void setTentative(boolean tentative) {
    this.tentative.set(tentative);
  }


  @Override
  public String toString() {
    return session.toString();
  }

  public Set<Integer> getUnitSemesters() {
    return session.getGroup().getUnit().getSemesters();
  }

  /**
   * <p>Calculates all semesters that this session is intended to be heard in. This is not
   * necessarily the same set of semesters as this session is provided in.</p>
   *
   * <p>Usually this is a subset of the provided semesters but it is in fact possible that those
   * sets are disjunctive. This can happen due to bad planning.</p>
   *
   * @return A set of semesters where this session should be heard in
   */
  public Set<Integer> getIntendedSemesters() {
    return session.getGroup().getUnit().getAbstractUnits().stream()
        .flatMap(abstractUnit -> abstractUnit.getModuleAbstractUnitSemesters().stream()
            .map(ModuleAbstractUnitSemester::getSemester))
        .collect(Collectors.toSet());
  }
}
