package com.example.featureToogler.service;

import com.example.featureToogler.dto.Feature;
import com.example.featureToogler.dto.UserFeature;
import com.example.featureToogler.repository.FeatureRepository;
import com.example.featureToogler.repository.UserFeatureRepository;
import com.example.featureToogler.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeatureService {

    @Autowired
    SecurityConfig asd;

    private final FeatureRepository featureRepository;

    private final UserFeatureRepository userFeatureRepository;


    public FeatureService(FeatureRepository featureRepository, UserFeatureRepository userFeatureRepository) {
        this.featureRepository = featureRepository;
        this.userFeatureRepository = userFeatureRepository;
    }

    public List<Feature> getFeatures() {
        return featureRepository.findAll();
    }

    public void createNewFeature(Feature feature) {
        featureRepository.save(feature);
    }

    public void editFeature(Long id, boolean isEnabled) {
        Optional.ofNullable(featureRepository.getById(id))
                .map(feature -> feature.setEnabledGlobally(isEnabled))
                .map(a -> featureRepository.save(a))
                .orElseThrow();
    }

    public void deleteFeature(Long id) {
        Optional.ofNullable(featureRepository.getById(id))
                .ifPresent(a -> featureRepository.delete(a));
    }

    public List<Feature> getEnabledFeatures() {
        return featureRepository.findEnabledFeatures();
    }

    public void enableUserFeature(Long userId, Long featureId) {
        List<Long> features = Optional.ofNullable(userFeatureRepository.findAllByUserId(userId))
                .map(userFeatures -> userFeatures.stream()
                        .map(UserFeature::getFeatureId)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        if (!features.contains(featureId)) {
            UserFeature userFeature = new UserFeature();
            userFeature.setUserId(userId);
            userFeature.setFeatureId(featureId);
            userFeatureRepository.save(userFeature);
        }
    }

    public List<UserFeature> getEnabledFeaturesOnlyForUser(Long userId) {
        List<UserFeature> features = new ArrayList<>();
        features.addAll(userFeatureRepository.findAllByUserId(userId));
        return features;
    }

    public List<Feature> getCommonEnabledAndUserFeatures(Long userId) {
        List<Feature> features = new ArrayList<>();
        features.addAll(getEnabledFeatures());
        features.addAll(featureRepository.findAllById(
                userFeatureRepository.findAllByUserId(userId).stream()
                        .map(UserFeature::getFeatureId)
                        .collect(Collectors.toSet())));
        return features;
    }

}
