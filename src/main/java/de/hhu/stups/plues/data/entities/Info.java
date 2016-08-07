package de.hhu.stups.plues.data.entities;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "info")
@Immutable
public class Info implements Serializable {

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
}
