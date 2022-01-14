package com.example.featureToogler.dto;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FEATURES")
public class Feature {

    private boolean enabledGlobally;

    public Feature() {
        enabledGlobally = false;
    }

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public boolean isEnabledGlobally() {
        return enabledGlobally;
    }

    public Feature setId(Long id) {
        this.id = id;
        return this;
    }

    public Feature setEnabledGlobally(boolean enabled) {
        this.enabledGlobally = enabled;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feature feature = (Feature) o;

        if (enabledGlobally != feature.enabledGlobally) return false;
        return id != null ? id.equals(feature.id) : feature.id == null;
    }

    @Override
    public int hashCode() {
        int result = (enabledGlobally ? 1 : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "enabledGlobally=" + enabledGlobally +
                ", id=" + id +
                '}';
    }
}
