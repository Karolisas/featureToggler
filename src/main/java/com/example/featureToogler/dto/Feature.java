package com.example.featureToogler.dto;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "FEATURES")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Feature {

    private boolean enabledGlobally;

    public Feature() {
        enabledGlobally = false;
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(mappedBy = "features", cascade = CascadeType.ALL)
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

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
        if (id != null ? !id.equals(feature.id) : feature.id != null) return false;
        return users != null ? users.equals(feature.users) : feature.users == null;
    }

    @Override
    public int hashCode() {
        int result = (enabledGlobally ? 1 : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "enabledGlobally=" + enabledGlobally +
                ", id=" + id +
                ", users=" + users +
                '}';
    }
}
