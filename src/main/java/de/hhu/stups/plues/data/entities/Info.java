package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "info")
@Immutable
public class Info implements Serializable {

    private static final long serialVersionUID = -995775189582480065L;
    @Id
    @NaturalId
    private String key;
    private String value;

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Info info = (Info) o;
        return Objects.equals(key, info.key) &&
                Objects.equals(value, info.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
