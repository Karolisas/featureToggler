package com.example.featureToogler.controller;

import com.example.featureToogler.dto.Feature;
import com.example.featureToogler.dto.UserFeature;
import com.example.featureToogler.service.UserFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = UserFeatureController.ENABLE_FEATURE_API_BASE_PATH, consumes = "application/json")
public class UserFeatureController {

    public static final String ENABLE_FEATURE_API_BASE_PATH = "/features/enabled"; //maybe users/{id}/features

    @Autowired
    private UserFeatureService service;

    @PutMapping() // maybe features/{featureId}/enabled
    public UserFeature enableUserFeature(@RequestParam Long userId, @RequestParam Long featureId) {
        return service.enableUserFeature(userId, featureId);
    }

    @GetMapping("/{userId}") // maybe features/enabled/users/{userId}
    public List<UserFeature> enabledOnlyForUserFeatures(@PathVariable Long userId) {
        return service.getEnabledFeaturesOnlyForUser(userId);
    }

    @GetMapping("/{userId}/all")
    public List<Feature> getGlobalEnabledAndUserFeatures(@PathVariable Long userId) {
        return service.getGlobalEnabledAndUserFeatures(userId);
    }
}
