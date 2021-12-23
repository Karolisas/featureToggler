package com.example.featureToogler.controller;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
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
    public String toString() {
        return "Feature{" +
                "enabledGlobally=" + enabledGlobally +
                ", id=" + id +
                '}';
    }
}
