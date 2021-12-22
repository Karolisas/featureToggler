package com.example.featureToogler.controller;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Feature {

    private final boolean enabled;

    public Feature() {
        enabled = false;
    }

    @Id
    @GeneratedValue
    private Long id;

    public boolean isEnabled() {
        return enabled;
    }

    public Long getId() {
        return id;
    }

    public Feature setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Feature{" +
                " id=" + id +
                ", enabled=" + enabled +
                '}';
    }
}
