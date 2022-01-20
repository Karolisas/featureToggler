package com.example.featureToogler.controller;

import com.example.featureToogler.dto.Feature;
import com.example.featureToogler.dto.UserFeature;
import com.example.featureToogler.service.UserFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = UserFeatureController.USER_FEATURE_API_BASE_PATH, consumes = "application/json")
public class UserFeatureController {

    public static final String USER_FEATURE_API_BASE_PATH = "/users"; //maybe users/{id}/features

    @Autowired
    private UserFeatureService service;

    @PutMapping("/{userId}/features/{featureId}") // maybe features/{featureId}/enabled
    public UserFeature enableUserFeature(@PathVariable Long userId, @PathVariable Long featureId) {
        return service.enableUserFeature(userId, featureId);
    }

    @GetMapping("/{userId}/features")
    public List<Feature> getGlobalEnabledAndUserFeatures(@PathVariable Long userId) {
        return service.getGlobalEnabledAndUserFeatures(userId);
    }

    @GetMapping("/{userId}/featuresOwn")
    public List<UserFeature> enabledOnlyForUserFeatures(@PathVariable Long userId) {
        return service.getEnabledFeaturesOnlyForUser(userId);
    }
}
