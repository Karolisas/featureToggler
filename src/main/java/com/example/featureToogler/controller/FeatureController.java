package com.example.featureToogler.controller;

import com.example.featureToogler.dto.Feature;
import com.example.featureToogler.dto.UserFeature;
import com.example.featureToogler.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(FeatureController.FEATURE_API_BASE_PATH)
public class FeatureController {

    public static final String FEATURE_API_BASE_PATH = "/feature";
    @Autowired
    private FeatureService service;

    @GetMapping
    public List<Feature> getAllFeatures() {
        return service.getFeatures();
    }

    @GetMapping("/common/enabled")
    public List<Feature> getAllEnabledFeatures() {
        return service.getEnabledFeatures();
    }

    @GetMapping("/{userId}/enabled")
    public List<Feature> getAllEnabledForUserFeatures(@PathVariable Long userId) {
        return service.getCommonEnabledAndUserFeatures(userId);
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
    public List<UserFeature> commonAndEnabledForUserFeatures(@PathVariable Long userId) {
       return service.getEnabledFeaturesOnlyForUser(userId);
    }

    @DeleteMapping
    public void deleteFeature(@RequestParam Long id) {
        service.deleteFeature(id);
    }

}
