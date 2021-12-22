package com.example.featureToogler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feature")
public class FeatureController {

    @Autowired
    private FeatureService service;

    @GetMapping
    public List<Feature> getAllFeatures() {
        return service.getFeatures();
    }

    @GetMapping("/enabled")
    public List<Feature> getAllEnabledFeatures() {
        return service.getEnabledFeatures();
    }

    @PostMapping
    public void createFeature() {
        service.createNewFeature(new Feature());
    }

    @PutMapping
    public void enableFeature(@RequestParam Long id, @RequestParam boolean isEnabled) {
        service.editFeature(id, isEnabled);
    }

    @DeleteMapping
    public void deleteFeature(@RequestParam Long id) {
        service.deleteFeature(id);
    }

    @GetMapping("/user")
    public String userFeature() {
        return "userFeature accesed";
    }

    @GetMapping("/admin")
    public String adminFeature() {
        return "adminFeature accesed";
    }
}
