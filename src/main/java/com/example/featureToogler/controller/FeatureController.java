package com.example.featureToogler.controller;

import com.example.featureToogler.dto.Feature;
import com.example.featureToogler.dto.UserFeature;
import com.example.featureToogler.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = FeatureController.FEATURE_API_BASE_PATH, consumes = "application/json")
public class FeatureController {

    public static final String FEATURE_API_BASE_PATH = "/features";

    @Autowired
    private FeatureService service;

    @GetMapping
    public List<Feature> getAllFeatures() {
        return service.getFeatures();
    }

    @PostMapping
    public Feature createFeature() {
        return service.createNewFeature(new Feature());
    }

    @PutMapping
    public Feature enableGlobalFeature(@RequestParam Long featureId, @RequestParam boolean isEnabled) {
        return service.enableDisableGlobalFeature(featureId, isEnabled);
    }

    @GetMapping("/enabled")
    public List<Feature> getEnabledGlobalFeatures() {
        return service.getEnabledGlobalFeatures();
    }

    @GetMapping("/enabled/{userId}")
    public List<Feature> getGlobalEnabledAndUserFeatures(@PathVariable Long userId) {
        return service.getGlobalEnabledAndUserFeatures(userId);
    }

    @PutMapping("/{userId}") //todo maybe /{featureId}/enabled{userId}
    public UserFeature enableUserFeature(@PathVariable Long userId, @RequestParam Long featureId) {
        return service.enableUserFeature(userId, featureId);
    }

    @GetMapping("/{userId}")
    public List<UserFeature> globalAndEnabledForUserFeatures(@PathVariable Long userId) {
        return service.getEnabledFeaturesOnlyForUser(userId);
    }

    @DeleteMapping
    public void deleteFeature(@RequestParam Long id) {
        service.deleteFeature(id);
    }

}
