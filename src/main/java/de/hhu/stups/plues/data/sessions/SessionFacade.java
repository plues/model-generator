package de.hhu.stups.plues.data.sessions;

import de.hhu.stups.plues.data.entities.AbstractUnit;
import de.hhu.stups.plues.data.entities.Course;
import de.hhu.stups.plues.data.entities.Module;
import de.hhu.stups.plues.data.entities.ModuleAbstractUnitSemester;
import de.hhu.stups.plues.data.entities.Session;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SessionFacade {
  private final Session session;

  private final ObjectProperty<Slot> slotObjectProperty = new SimpleObjectProperty<>();
  private final Set<Course> courses;

  /**
   * A class that facades a session for ui representation.
   *
   * @param session the session to facade
   */
  public SessionFacade(final Session session) {
    this.session = session;
    courses = session.getGroup().getUnit().getAbstractUnits().parallelStream()
      .map(AbstractUnit::getModules)
      .flatMap(Set::parallelStream)
      .map(Module::getCourses)
      .flatMap(Set::parallelStream)
      .collect(Collectors.toSet());

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

    /**
     * Create a new Slot object.
     * @param day DayOfWeek for the slot
     * @param time integer representing the time slot
     */
    public Slot(final DayOfWeek day, final Integer time) {
      this.day = day;
      this.time = time;

      dayMap.put(DayOfWeek.MONDAY, "mon");
      dayMap.put(DayOfWeek.TUESDAY, "tue");
      dayMap.put(DayOfWeek.WEDNESDAY, "wed");
      dayMap.put(DayOfWeek.THURSDAY, "thu");
      dayMap.put(DayOfWeek.FRIDAY, "fri");
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
      String timeString = String.valueOf(6 + time * 2) + ":30";
      return String.format("%s, %s", getDayString(), timeString);
    }

    public Integer getTime() {
      return time;
    }
  }

  public boolean isTentative() {
    return session.isTentative();
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
   * sets are disjunct. This can happen due to bad planning.</p>
   *
   * @return A set of semesters where this session should be heard in
   */
  public Set<Integer> getIntendedSemesters() {
    return session.getGroup().getUnit().getAbstractUnits().stream()
        .flatMap(abstractUnit -> abstractUnit.getModuleAbstractUnitSemesters().stream()
            .map(ModuleAbstractUnitSemester::getSemester))
        .collect(Collectors.toSet());
  }

  /**
   * Calculates all courses that this session is part of.
   *
   * @return A set of courses that this session is part of
   */
  public Set<Course> getIntendedCourses() {
    return courses;
  }

  public Set<AbstractUnit> getIntendedAbstractUnits() {
    return new HashSet<>(session.getGroup().getUnit().getAbstractUnits());
  }
}
