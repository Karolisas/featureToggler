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

    @PutMapping("/{userId}")
    public void enableUserFeature(@PathVariable Long userId, @RequestParam Long featureId) {
        service.enableUserFeature(userId, featureId);
    }

    @GetMapping("/{userId}")
    public List<UserFeature> enableUserFeature(@PathVariable Long userId) {
       return service.getEanbledFeaturesForUser(userId);
    }

    @DeleteMapping
    public void deleteFeature(@RequestParam Long id) {
        service.deleteFeature(id);
    }

}
