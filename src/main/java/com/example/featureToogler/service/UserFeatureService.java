package com.example.featureToogler.service;

import com.example.featureToogler.dto.Feature;
import com.example.featureToogler.dto.UserFeature;
import com.example.featureToogler.repository.UserFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserFeatureService {

    private final UserFeatureRepository userFeatureRepository;

    @Autowired
    private FeatureService featureService;

    public UserFeatureService(UserFeatureRepository userFeatureRepository) {
        this.userFeatureRepository = userFeatureRepository;
    }

    /**
     * Feature is enabled only for selected user by passing userId and featureId
     * @param userId The user id
     * @param featureId The feature id
     * @return
     */
    public UserFeature enableUserFeature(Long userId, Long featureId) {
        List<Long> features = Optional.ofNullable(userFeatureRepository.findAllByUserId(userId))
                .map(userFeatures -> userFeatures.stream()
                        .map(UserFeature::getFeatureId)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        UserFeature userFeature = new UserFeature();
        if (!features.contains(featureId)) {
            userFeature = new UserFeature();
            userFeature.setUserId(userId);
            userFeature.setFeatureId(featureId);
            userFeatureRepository.save(userFeature);
        }
        return userFeature;
    }

    /**
     * Returns the list of Features enabled personally only for the user
     * @param userId The user id
     * @return
     */
    public List<UserFeature> getEnabledFeaturesOnlyForUser(Long userId) {
        List<UserFeature> features = new ArrayList<>();
        features.addAll(userFeatureRepository.findAllByUserId(userId));
        return features;
    }

    /**
     * Returns the list of Features enabled both globally and personally for the user
     * @param userId The user id
     * @return
     */
        public List<Feature> getGlobalEnabledAndUserFeatures(Long userId) {
        List<Feature> features = new ArrayList<>();
        features.addAll(featureService.getEnabledGlobalFeatures());
        features.addAll(featureService.findFeaturesById(
                userFeatureRepository.findAllByUserId(userId).stream()
                        .map(UserFeature::getFeatureId)
                        .collect(Collectors.toSet())));
        return features;
    }
}
