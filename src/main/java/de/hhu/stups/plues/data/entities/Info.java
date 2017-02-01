package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "info")
@Immutable
public class Info implements Serializable {

  private static final long serialVersionUID = -995775189582480065L;

  @Id
  @NaturalId
  private String key;

  private String value;

  public Info() {
    // Default constructor is required by hibernate
  }

  public String getKey() {
    return this.key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    final Info info = (Info) other;
    return Objects.equals(key, info.key)
        && Objects.equals(value, info.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value);
  }
}
